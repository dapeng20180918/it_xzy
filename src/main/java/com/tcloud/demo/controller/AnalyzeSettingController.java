package com.tcloud.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.dao.impl.AnalyzeSettingDao;
import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.model.AnalyzeSetting;
import com.tcloud.demo.model.Event;
import com.tcloud.demo.utils.MessageBundle;

@RestController
@RequestMapping("/rest")
@Transactional(rollbackFor=Exception.class)
public class AnalyzeSettingController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(AnalyzeSettingController.class);
	
	@Autowired
	AnalyzeSettingDao analyzeSettingDao;
	
	@Autowired
	EventDao eventDao;
	//gets
	@GetMapping(value = "/analyzesetting")
	@ResponseBody
	public AnalyzeSetting get() {
//		String val = "/rest/analyzesetting/getAll, operator:{}";
//		logger.info(val, getUser());
		return analyzeSettingDao.getAll().get(0);
	}
	
	//update
	@PostMapping(value = "/analyzesetting/update")
	@ResponseBody
	public AnalyzeSetting update(@RequestBody AnalyzeSetting analyzesetting) {
		String val = "/rest/analyzesetting/update, operator:{}";
		logger.info(val, getUser());
		Event event = new Event();
		event.setType(MessageBundle.ANALYZE_SETTING_UPDATE);
		event.setOperator(getUser());
		eventDao.create(event);
		
		return analyzeSettingDao.update(analyzesetting);
	}
	
}
