package com.tcloud.demo.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tcloud.demo.dao.impl.AnalyzeTaskDao;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks{
	private static Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	@Autowired
	AnalyzeTaskDao analyzeTaskDao;
	
	@Value("${spring.sub.minute}")
	public int subminute;
	
	@Scheduled(cron = "${schedule.cron}")
//	@Scheduled(fixedRate = 1000 * 60)
	public void doScheduled(){
		//update task
//		logger.info("------------Task Schedule Start------------");
		analyzeTaskDao.updateOneTask(subminute);
	}
	

}