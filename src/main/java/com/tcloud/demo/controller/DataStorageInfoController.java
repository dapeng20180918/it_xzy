package com.tcloud.demo.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcloud.demo.dao.impl.DataStorageInfoDao;
import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.model.DataStorageInfo;
import com.tcloud.demo.model.Event;
import com.tcloud.demo.utils.MessageBundle;

@RestController
public class DataStorageInfoController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DataStorageInfoController.class);

	@Autowired
	DataStorageInfoDao dataStorageInfoDao;

	@Autowired
	EventDao eventDao;
	// gets
	@GetMapping(value = "/rest/datastorageinfo")
	@ResponseBody
	public List<DataStorageInfo> getAll() {
//		String val = "/rest/dataStorageInfo/getAll, operator:{}";
//		logger.info(val, getUser());
		return dataStorageInfoDao.getAll();
	}
	
	// get by parentid
	@GetMapping(value = "/rest/datastorageinfo/{id}")
	@ResponseBody
	public List<DataStorageInfo> findByParent(@PathVariable("id") Integer id) {
//		String val = "/rest/datastorageinfo/parent:{}, operator:{}";
//		logger.info(val, id, getUser());
		return dataStorageInfoDao.findByParent(id);
	}

	// create
	@PostMapping(value = "/rest/datastorageinfo/create")
	@ResponseBody
	public DataStorageInfo create(@RequestBody DataStorageInfo info) {
		String fileName = info.getStorage_location();
		if(fileName == null){
			fileName = UUID.randomUUID().toString().replace("-", "");
			info.setStorage_location(fileName);
		}
		String val = "/rest/dataStorageInfo/create:{}, operator:{}" ;
		logger.info(val, fileName, getUser());
		
		Event event = new Event();
		event.setType(MessageBundle.DATA_STORAGE_INFO_CREATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", fileName));
		eventDao.create(event);
		
		return dataStorageInfoDao.create(info);
	}
}
