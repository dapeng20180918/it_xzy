package com.tcloud.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.dao.impl.DataCollectorDao;
import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.model.Event;
import com.tcloud.demo.model.Response;
import com.tcloud.demo.model.DataCollector;
import com.tcloud.demo.utils.MessageBundle;

@RestController
@RequestMapping("/rest")
@Transactional(rollbackFor=Exception.class)
public class DataCollectorController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DataCollectorController.class);
	
	@Autowired
	DataCollectorDao dataCollectorDao;
	
	@Autowired
	EventDao eventDao;
	//gets
	@GetMapping(value = "/datacollector")
	@ResponseBody
	public List<DataCollector> get() {
//		String val = "/rest/datacollector/getAll, operator:{}";
//		logger.info(val, getUser());
		return dataCollectorDao.getAll();
	}
	
	//create
	@PostMapping(value = "/datacollector/create")
	@ResponseBody
	public DataCollector create(@RequestBody DataCollector datacollector) {
		String val = "/rest/datacollector/create:{}, operator:{}";
		logger.info(val, datacollector.getName(), getUser());
		datacollector.setUser_name(getUser());
		Event event = new Event();
		event.setType(MessageBundle.DATA_COLLECTOR_CREATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", datacollector.getName()));
		eventDao.create(event);
		
		return dataCollectorDao.create(datacollector);
	}
	
	//update
	@PostMapping(value = "/datacollector/update")
	@ResponseBody
	public DataCollector update(@RequestBody DataCollector datacollector) {
		String val = "/rest/datacollector/update:{}, id:{}, operator:{}" ;
		logger.info(val, datacollector.getName(), datacollector.getId(), getUser());
		Event event = new Event();
		event.setType(MessageBundle.DATA_COLLECTOR_UPDATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", datacollector.getName()));
		eventDao.create(event);
		
		return dataCollectorDao.update(datacollector);
	}
	
	//delete
	@DeleteMapping(value = "/datacollector/{id}")
	@ResponseBody
	public Response delete(@PathVariable("id") Integer id) {
		DataCollector dc = dataCollectorDao.getOne(id);
		String val = "/rest/datacollector/delete:{}, id:{}, operator:{}";
		logger.info(val, dc.getName(), id, getUser());
		dataCollectorDao.delete(id);
		
		Event event = new Event();
		event.setType(MessageBundle.DATA_COLLECTOR_DELETE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", dc.getName()));
		eventDao.create(event);
		
		return new Response("success", "");
	}
}
