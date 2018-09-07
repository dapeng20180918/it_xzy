package com.tcloud.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.dao.impl.MessageDao;
import com.tcloud.demo.dao.impl.RuleDao;
import com.tcloud.demo.model.Event;
import com.tcloud.demo.model.Message;
import com.tcloud.demo.model.Response;
import com.tcloud.demo.model.Rule;

@RestController
public class QueryController extends BaseController{
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(QueryController.class);
	
	@Autowired
	RuleDao ruleDao;
	
	@Autowired
	EventDao eventDao;
	
	@Autowired
	MessageDao messageDao;
	
	//gets rule
	@GetMapping(value = "/rest/rule")
	@ResponseBody
	public List<Rule> getRules() {
//		String val = "/rest/rule/getAll, operator:{}";
//		logger.info(val, getUser());
		return ruleDao.getAll();
	}

	//gets message
	@GetMapping(value = "/rest/message")
	@ResponseBody
	public List<Message> getMessages() {
		return messageDao.findByOperatorAndReaded(getUser());
	}
	
	@PostMapping(value = "/rest/message/update")
	@ResponseBody
	public Response updateMessages() {
		messageDao.readMessages(getUser());
		return new Response("success", "");
	}
	
	//gets event
	@GetMapping(value = "/rest/event")
	@ResponseBody
	public List<Event> getEvents() {
//		String val = "/rest/event/getAll, operator:{}";
//		logger.info(val, getUser());
		List<Event> events = eventDao.getAll();
		for(Event e : events){
			e.setTypeMessage();
		}
		return events;
	}
}
