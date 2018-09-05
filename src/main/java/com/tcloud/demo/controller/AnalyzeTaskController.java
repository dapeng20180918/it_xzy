package com.tcloud.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.dao.impl.AnalyzeTaskDao;
import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.model.Event;
import com.tcloud.demo.model.Response;
import com.tcloud.demo.model.AnalyzeTask;
import com.tcloud.demo.utils.MessageBundle;

@RestController
@RequestMapping("/rest")
public class AnalyzeTaskController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(AnalyzeTaskController.class);
	
	@Autowired
	AnalyzeTaskDao analyzeTaskDao;
	
	@Autowired
	EventDao eventDao;
	//gets
	@GetMapping(value = "/analyzetask")
	@ResponseBody
	public List<AnalyzeTask> get(String type) {
//		String val = "/rest/analyzetask/getAll, operator:{}";
		List<AnalyzeTask> ats = null;
		if(type!=null){
//			val = "/rest/analyzetask/getAll:{}, operator:{}";
//			logger.info(val, type, getUser());
			ats = analyzeTaskDao.findByType(type, getUser());
		}else{
//			logger.info(val, getUser());
			ats = analyzeTaskDao.getAll();
		}
		
		for(AnalyzeTask at : ats){
			at.fillHosts();
		}
		return ats;
	}
	
	//create
	@PostMapping(value = "/analyzetask/create")
	@ResponseBody
	public AnalyzeTask create(@RequestBody AnalyzeTask analyzetask) {
		String val = "/rest/analyzetask/create:{}, operator:{}" ;
		logger.info(val, analyzetask.getName(), getUser());
		analyzetask.setUser_name(getUser());
		analyzetask.selectHosts();
		Event event = new Event();
		event.setType(MessageBundle.ANALYZE_TASK_CREATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", analyzetask.getName()));
		eventDao.create(event);
		
		return analyzeTaskDao.create(analyzetask);
	}
	
	//update
//	@PostMapping(value = "/analyzetask/update")
	@ResponseBody
	public AnalyzeTask update(@RequestBody AnalyzeTask analyzetask) {
		String val = "/rest/analyzetask/update:{}, operator:{}" ;
		logger.info(val, analyzetask.getName(), getUser());
		return analyzeTaskDao.update(analyzetask);
	}
	
	//delete
//	@DeleteMapping(value = "/analyzetask/{id}")
	@ResponseBody
	public Response delete(@PathVariable("id") Integer id) {
		String val = "/rest/analyzetask/delete:{}, operator:{}" ;
		logger.info(val, id, getUser());
		analyzeTaskDao.delete(id);
		return new Response("success", "");
	}
}
