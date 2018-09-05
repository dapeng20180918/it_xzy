package com.tcloud.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.dao.impl.AnalyzeAdapterDao;
import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.model.Event;
import com.tcloud.demo.model.Response;
import com.tcloud.demo.model.AnalyzeAdapter;
import com.tcloud.demo.utils.MessageBundle;

@RestController
@RequestMapping("/rest")
public class AnalyzeAdapterController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(AnalyzeAdapterController.class);
	
	@Autowired
	AnalyzeAdapterDao analyzeAdapterDao;
	
	@Autowired
	EventDao eventDao;
	//gets
	@GetMapping(value = "/analyzeadapter")
	@ResponseBody
	public List<AnalyzeAdapter> get() {
//		String val = "/rest/analyzeadapter/getAll, operator:{}";
//		logger.info(val, getUser());
		return analyzeAdapterDao.getAll();
	}
	
	//create
	@PostMapping(value = "/analyzeadapter/create")
	@ResponseBody
	public AnalyzeAdapter create(@RequestBody AnalyzeAdapter analyzeadapter) {
		String val = "/rest/analyzeadapter/create:{}, operator:{}";
		logger.info(val, analyzeadapter.getName(), getUser());
		analyzeadapter.setUser_name(getUser());
		Event event = new Event();
		event.setType(MessageBundle.ANALYZE_ADAPTER_CREATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", analyzeadapter.getName()));
		eventDao.create(event);
		
		return analyzeAdapterDao.create(analyzeadapter);
	}
	
	//update
	@PostMapping(value = "/analyzeadapter/update")
	@ResponseBody
	public AnalyzeAdapter update(@RequestBody AnalyzeAdapter analyzeadapter) {
		String val = "/rest/analyzeadapter/update:{}, id:{}, operator:{}" ;
		logger.info(val, analyzeadapter.getName(), analyzeadapter.getId(), getUser());
		Event event = new Event();
		event.setType(MessageBundle.ANALYZE_ADAPTER_UPDATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", analyzeadapter.getName()));
		eventDao.create(event);
		
		return analyzeAdapterDao.update(analyzeadapter);
	}
	
	//delete
	@DeleteMapping(value = "/analyzeadapter/{id}")
	@ResponseBody
	public Response delete(@PathVariable("id") Integer id) {
		AnalyzeAdapter analyzeadapter = analyzeAdapterDao.getOne(id);
		String val = "/rest/analyzeadapter/delete:{}, id:{}, operator:{}" ;
		logger.info(val, analyzeadapter.getName(), id, getUser());
		analyzeAdapterDao.delete(id);
		
		Event event = new Event();
		event.setType(MessageBundle.ANALYZE_ADAPTER_DELETE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", analyzeadapter.getName()));
		eventDao.create(event);
		
		return new Response("success", "");
	}
}
