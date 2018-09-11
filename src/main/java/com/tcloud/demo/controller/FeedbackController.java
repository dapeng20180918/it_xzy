package com.tcloud.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.dao.impl.FeedbackDao;
import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.model.Feedback;
import com.tcloud.demo.model.Event;
import com.tcloud.demo.utils.MessageBundle;

@RestController
public class FeedbackController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

	@Autowired
	FeedbackDao feedbackDao;

	@Autowired
	EventDao eventDao;
	// gets
	@GetMapping(value = "/rest/feedback")
	@ResponseBody
	public List<Feedback> getAll() {
		return feedbackDao.getAll();
	}

	// create
	@PostMapping(value = "/rest/feedback/create")
	@ResponseBody
	public Feedback create(@RequestBody Feedback info) {
		String val = "/rest/feedback/create:{}, operator:{}" ;
		info.setOperator(getUser());
		logger.info(val, info.getName(), getUser());
		
		Event event = new Event();
		event.setType(MessageBundle.FEEDBACK_CREATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", info.getName()));
		eventDao.create(event);
		
		return feedbackDao.create(info);
	}
	
	@PostMapping(value = "/rest/feedback/update")
	@ResponseBody
	public void addCount(@RequestBody Feedback info) {
//		String val = "/rest/feedback/update:{}, operator:{}" ;
//		logger.info(val, info.getName(), getUser());
		feedbackDao.addCount(info);
	}
}
