package com.tcloud.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.dao.impl.AnalyzeStrategyDao;
import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.model.Event;
import com.tcloud.demo.model.Response;
import com.tcloud.demo.model.AnalyzeStrategy;
import com.tcloud.demo.utils.MessageBundle;

@RestController
@RequestMapping("/rest")
@Transactional(rollbackFor=Exception.class)
public class AnalyzeStrategyController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(AnalyzeStrategyController.class);
	
	@Autowired
	AnalyzeStrategyDao analyzeStrategyDao;
	
	@Autowired
	EventDao eventDao;
	//gets
	@GetMapping(value = "/analyzestrategy")
	@ResponseBody
	public List<AnalyzeStrategy> get(String type) {
//		String val = "/rest/analyzestrategy/getAll, operator:{}";
		if(type!=null){
//			val = "/rest/analyzestrategy/getAll:{}, operator:{}";
//			logger.info(val, type, getUser());
			return analyzeStrategyDao.findByType(type);
		}
		
//		logger.info(val, getUser());
		return analyzeStrategyDao.getAll();
	}
	
	//create
	@PostMapping(value = "/analyzestrategy/create")
	@ResponseBody
	public AnalyzeStrategy create(@RequestBody AnalyzeStrategy analyzestrategy) {
		String val = "/rest/analyzestrategy/create:{}, operator:{}";
		logger.info(val, analyzestrategy.getName(), getUser());
		analyzestrategy.setUser_name(getUser());
		Event event = new Event();
		event.setType(MessageBundle.ANALYZE_STRATEGY_CREATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", analyzestrategy.getName()));
		eventDao.create(event);
		
		return analyzeStrategyDao.create(analyzestrategy);
	}
	
	//update
	@PostMapping(value = "/analyzestrategy/update")
	@ResponseBody
	public AnalyzeStrategy update(@RequestBody AnalyzeStrategy analyzestrategy) {
		String val = "/rest/analyzestrategy/update:{}, id:{}, operator:{}" ;
		logger.info(val, analyzestrategy.getName(), analyzestrategy.getId(), getUser());
		Event event = new Event();
		event.setType(MessageBundle.ANALYZE_STRATEGY_UPDATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", analyzestrategy.getName()));
		eventDao.create(event);
		
		return analyzeStrategyDao.update(analyzestrategy);
	}
	
	//delete
	@DeleteMapping(value = "/analyzestrategy/{id}")
	@ResponseBody
	public Response delete(@PathVariable("id") Integer id) {
		AnalyzeStrategy as = analyzeStrategyDao.getOne(id);
		String val = "/rest/analyzestrategy/delete:{}, id:{}, operator:{}";
		logger.info(val, as.getName(), id, getUser());
		analyzeStrategyDao.delete(id);
		
		Event event = new Event();
		event.setType(MessageBundle.ANALYZE_STRATEGY_DELETE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", as.getName()));
		eventDao.create(event);
		
		return new Response("success", "");
	}
}
