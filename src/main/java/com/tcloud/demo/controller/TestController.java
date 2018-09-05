package com.tcloud.demo.controller;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.model.Boy;
import com.tcloud.demo.model.Response;
//only test
//@RequestMapping("/rest")
//@RestController
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	//get one
//	@GetMapping(value = "/test/{id}")
	@ResponseBody
	public Response getOne(@PathVariable("id") Integer id) {
		String val = "/rest/test/get:" + id;
		logger.debug(val);
		Response resp = new Response("success", val);
		return resp;
	}
	
	//get all
//	@GetMapping(value = "/test")
	@ResponseBody
	public List<Response> getAll() {
		String val = "/rest/test/getAll:";
		logger.debug(val);
		List<Response> lists = new ArrayList<Response>();
		Response resp = new Response("success", val);
		lists.add(resp);
		return lists;
	}
	
	//create
//	@PostMapping(value = "/test/create")
	@ResponseBody
	public Response create(@RequestBody Boy boy) {
		String val = "/rest/test/create:" + boy.getName();
		logger.debug(val);
		Response resp = new Response("success", val);
		return resp;
	}
	
	//update
//	@PostMapping(value = "/test/update")
	@ResponseBody
	public Response update(@RequestBody Boy boy) {
		String val = "/rest/test/update:" + boy.getName();
		logger.debug(val);
		Response resp = new Response("success", val);
		return resp;
	}
	
	//delete
//	@DeleteMapping(value = "/test/{id}")
	@ResponseBody
	public Response delete(@PathVariable("id") Integer id) {
		String val = "/rest/test/delete:" + id;
		logger.debug(val);
		Response resp = new Response("success", val);
		return resp;
	}
}
