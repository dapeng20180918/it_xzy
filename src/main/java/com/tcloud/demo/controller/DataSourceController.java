package com.tcloud.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.dao.impl.DataSourceDao;
import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.model.Event;
import com.tcloud.demo.model.Response;
import com.tcloud.demo.model.DataSource;
import com.tcloud.demo.utils.MessageBundle;

@RestController
@RequestMapping("/rest")
@Transactional(rollbackFor=Exception.class)
public class DataSourceController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DataSourceController.class);
	
	@Autowired
	DataSourceDao dataSourceDao;
	
	@Autowired
	EventDao eventDao;
	//gets
	@GetMapping(value = "/datasource")
	@ResponseBody
	public List<DataSource> get() {
//		String val = "/rest/datasource/getAll, operator:{}";
//		logger.info(val, getUser());
		return dataSourceDao.getAll();
	}
	
	//create
	@PostMapping(value = "/datasource/create")
	@ResponseBody
	public DataSource create(@RequestBody DataSource datasource) {
		String val = "/rest/datasource/create:{}, operator:{}" ;
		logger.info(val, datasource.getName(), getUser());
		datasource.setUser_name(getUser());
		Event event = new Event();
		event.setType(MessageBundle.DATA_SOURCE_CREATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", datasource.getName()));
		eventDao.create(event);
		
		return dataSourceDao.create(datasource);
	}
	
	//update
	@PostMapping(value = "/datasource/update")
	@ResponseBody
	public DataSource update(@RequestBody DataSource datasource) {
		String val = "/rest/datasource/update:{}, id:{}, operator:{}" ;
		logger.info(val, datasource.getName(), datasource.getId(), getUser());
		Event event = new Event();
		event.setType(MessageBundle.DATA_SOURCE_UPDATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", datasource.getName()));
		eventDao.create(event);
		
		return dataSourceDao.update(datasource);
	}
	
	//delete
	@DeleteMapping(value = "/datasource/{id}")
	@ResponseBody
	public Response delete(@PathVariable("id") Integer id) {
		DataSource ds = dataSourceDao.getOne(id);
		String val = "/rest/datasource/delete:{}, id:{}, operator:{}" ;
		logger.info(val, ds.getName(), id, getUser());
		dataSourceDao.delete(id);
		
		Event event = new Event();
		event.setType(MessageBundle.DATA_SOURCE_DELETE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", ds.getName()));
		eventDao.create(event);
		
		return new Response("success", "");
	}
}
