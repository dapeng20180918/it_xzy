package com.tcloud.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.dao.impl.RoleDao;
import com.tcloud.demo.model.Event;
import com.tcloud.demo.model.Response;
import com.tcloud.demo.model.Role;
import com.tcloud.demo.utils.MessageBundle;

@RestController
@RequestMapping("/rest")
@Transactional(rollbackFor=Exception.class)
public class RoleController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	EventDao eventDao;
	
	//gets
	@GetMapping(value = "/role")
	@ResponseBody
	public List<Role> get() {
//		String val = "/rest/role/getAll, operator:{}";
//		logger.info(val, getUser());
		return roleDao.getAll();
	}
	
	//create
	@PostMapping(value = "/role/create")
	@ResponseBody
	public Role create(@RequestBody Role role) {
		String val = "/rest/role/create:{}, operator:{}";
		logger.info(val, role.getName(), getUser());
		Event event = new Event();
		event.setType(MessageBundle.ROLE_CREATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", role.getName()));
		eventDao.create(event);
		
		return roleDao.create(role);
	}
	
	//update
	@PostMapping(value = "/role/update")
	@ResponseBody
	public Role update(@RequestBody Role role) {
		String val = "/rest/role/update:{}, id:{}, operator:{}";
		logger.info(val, role.getName(), role.getId(), getUser());
		Event event = new Event();
		event.setType(MessageBundle.ROLE_UPDATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", role.getName()));
		eventDao.create(event);
		
		return roleDao.update(role);
	}
	
	//delete
	@DeleteMapping(value = "/role/{id}")
	@ResponseBody
	public Response delete(@PathVariable("id") Integer id) {
		Role role = roleDao.getOne(id);
		roleDao.delete(id);
		
		String val = "/rest/role/delete:{}, id:{}, operator:{}";
		logger.info(val, role.getName(), id, getUser());
		Event event = new Event();
		event.setType(MessageBundle.ROLE_DELETE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", role.getName()));
		eventDao.create(event);
		
		return new Response("success", "");
	}
}
