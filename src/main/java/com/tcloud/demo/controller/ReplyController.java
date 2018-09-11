package com.tcloud.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.dao.impl.ReplyDao;
import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.model.Reply;

@RestController
public class ReplyController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

	@Autowired
	ReplyDao replyDao;

	@Autowired
	EventDao eventDao;
	
	// gets
	@GetMapping(value = "/rest/reply/{id}")
	@ResponseBody
	public List<Reply> getByTopic(@PathVariable("id") Integer id) {
		return replyDao.getByTopic(id);
	}

	// create
	@PostMapping(value = "/rest/reply/create")
	@ResponseBody
	public Reply create(@RequestBody Reply info) {
		String val = "/rest/reply/create:{}, operator:{}" ;
		info.setOperator(getUser());
		logger.info(val, info.getName(), getUser());
		return replyDao.create(info);
	}
}
