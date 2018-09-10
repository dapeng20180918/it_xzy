package com.tcloud.demo.scheduler;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tcloud.demo.dao.impl.AnalyzeTaskDao;
import com.tcloud.demo.dao.impl.DataCollectScheduleDao;
import com.tcloud.demo.dao.impl.DataCollectorDao;
import com.tcloud.demo.dao.impl.DataStorageInfoDao;
import com.tcloud.demo.dao.impl.MessageDao;
import com.tcloud.demo.model.AnalyzeTask;
import com.tcloud.demo.model.DataCollectSchedule;
import com.tcloud.demo.model.DataCollector;
import com.tcloud.demo.model.DataStorageInfo;
import com.tcloud.demo.model.Message;

import org.quartz.CronExpression;

import java.util.ArrayList;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {
	private static Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

	@Autowired
	AnalyzeTaskDao analyzeTaskDao;

	@Autowired
	DataCollectScheduleDao dataCollectScheduleDao;
	
	@Autowired
	DataCollectorDao dataCollectorDao;
	
	@Autowired
	DataStorageInfoDao dataStorageInfoDao;
	
	@Autowired
	MessageDao messageDao;

	@Value("${spring.sub.minute}")
	public int subminute;

	@Scheduled(cron = "${schedule.cron}")
	// @Scheduled(fixedRate = 1000 * 60)
	public void doScheduled() {
		// update task
		AnalyzeTask analyzeTask = analyzeTaskDao.updateOneTask(subminute);
		if (analyzeTask == null) {
			return;
		}
		Message msg = new Message();
		msg.setOperator(analyzeTask.getUser_name());
		msg.setCreate_date(analyzeTask.getFinish_date());
		String out = "Task " + analyzeTask.getName() + " finished";
		logger.info("[{}] {}", analyzeTask.getFinish_date(), out);
		msg.setDescription(out);
		messageDao.create(msg);
	}

	@Scheduled(cron = "${schedule.cron}")
	public void doDataColectScheduled() {
		Date to = new Date();// now
		// select Schedule from db
		List<DataCollectSchedule> dataCollectSchedules = dataCollectScheduleDao.getRunningSchedules();
		List<DataCollectSchedule> ignoreSchedules = new ArrayList<DataCollectSchedule>();
		
		for (DataCollectSchedule schedule : dataCollectSchedules) {
			try {
				Date nextTime = schedule.getUpdate_time();
				if(nextTime == null){
					nextTime = schedule.getCreate_time();
				}
				// check cron format, throw exception
				CronExpression expression = new CronExpression(schedule.getCrontab());
		        List<Date> crontimes = new ArrayList<Date>();
				while (true) {
					nextTime = expression.getNextValidTimeAfter(nextTime);
					if (nextTime.getTime() > to.getTime())
						break;
					crontimes.add(nextTime);
				}
				
				if(crontimes.size() >= 1){
					nextTime = crontimes.get(crontimes.size() - 1);

					// update data storage
					DataCollector dc = dataCollectorDao.getOne(schedule.getDatacollector_id());
					DataStorageInfo dsi = dataStorageInfoDao.findByName(dc.getStorage_location());
					int size = (int) (Math.random() * 15 + 5);
					if(dsi != null){
						//update
						dsi.setData_size(dsi.getData_size() + size);
						dsi.setLast_time(nextTime);
						dataStorageInfoDao.update(dsi);
						logger.info("dataStorageInfo update, name: {}, lastTime: {}, dSize: {}", 
								dsi.getStorage_location(), dsi.getLast_time(), dsi.getData_size());
					}else{
						//insert
						dsi = new DataStorageInfo();
						dsi.setLast_time(nextTime);
						dsi.setData_size(new Long(size));
						dsi.setStorage_location(dc.getStorage_location());
						dsi.setStorage_type(dc.getStorage_type());
						dsi.setParent(dc.getTree_id());
						dataStorageInfoDao.create(dsi);
						logger.info("dataStorageInfo create, name: {}, lastTime: {}, dSize: {}", 
								dsi.getStorage_location(), dsi.getLast_time(), dsi.getData_size());
					}
					
					// update schedule
					schedule.setUpdate_time(nextTime);
					dataCollectScheduleDao.update(schedule);
					logger.info("dataCollectSchedule update, name: {}, updateTime: {}",
							schedule.getName(), schedule.getUpdate_time());
				}
		        
			} catch (Exception e) {
				// some error, ignore this one
				ignoreSchedules.add(schedule);
			}
		}

	}

}