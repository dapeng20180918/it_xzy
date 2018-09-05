package com.tcloud.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.dao.impl.DataCollectScheduleDao;
import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.model.Event;
import com.tcloud.demo.model.Response;
import com.tcloud.demo.model.DataCollectSchedule;
import com.tcloud.demo.utils.MessageBundle;

@RestController
@RequestMapping("/rest")
public class DataCollectScheduleController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DataCollectScheduleController.class);
	
	@Autowired
	DataCollectScheduleDao dataCollectorScheduleDao;
	
	@Autowired
	EventDao eventDao;
	//gets
	@GetMapping(value = "/datacollectschedule")
	@ResponseBody
	public List<DataCollectSchedule> get() {
//		String val = "/rest/datacollectschedule/getAll, operator:{}";
//		logger.info(val, getUser());
		return dataCollectorScheduleDao.getAll();
	}
	
	//create
	@PostMapping(value = "/datacollectschedule/create")
	@ResponseBody
	public DataCollectSchedule create(@RequestBody DataCollectSchedule datacollectschedule) {
		String val = "/rest/datacollectschedule/create:{}, operator:{}";
		logger.info(val, datacollectschedule.getName(), getUser());
		datacollectschedule.setUser_name(getUser());
		Event event = new Event();
		event.setType(MessageBundle.DATA_COLLECT_SCHEDULE_CREATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", datacollectschedule.getName()));
		eventDao.create(event);
		
		return dataCollectorScheduleDao.create(datacollectschedule);
	}
	
	//update
	@PostMapping(value = "/datacollectschedule/update")
	@ResponseBody
	public DataCollectSchedule update(@RequestBody DataCollectSchedule datacollectschedule) {
		String val = "/rest/datacollectschedule/update:{}, id:{}, operator:{}" ;
		logger.info(val, datacollectschedule.getName(), datacollectschedule.getId(), getUser());
		Event event = new Event();
		event.setType(MessageBundle.DATA_COLLECT_SCHEDULE_UPDATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", datacollectschedule.getName()));
		eventDao.create(event);
		
		return dataCollectorScheduleDao.update(datacollectschedule);
	}
	
	//delete
	@DeleteMapping(value = "/datacollectschedule/{id}")
	@ResponseBody
	public Response delete(@PathVariable("id") Integer id) {
		DataCollectSchedule dcs = dataCollectorScheduleDao.getOne(id);
		String val = "/rest/datacollectschedule/delete:{}, id:{}, operator:{}";
		logger.info(val, dcs.getName(), id, getUser());
		dataCollectorScheduleDao.delete(id);
		
		Event event = new Event();
		event.setType(MessageBundle.DATA_COLLECT_SCHEDULE_DELETE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", dcs.getName()));
		eventDao.create(event);
		
		return new Response("success", "");
	}
}
