package com.tcloud.demo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.DataStorageInfoRepository;
import com.tcloud.demo.model.DataStorageInfo;

@Repository
public class DataStorageInfoDao implements DaoTemplate<DataStorageInfo> {
	@Autowired
	public DataStorageInfoRepository repo;

	@Override
	public List<DataStorageInfo> getAll() {
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		List<DataStorageInfo> users = repo.findAll(sort);
		return users;
	}
	
	public List<DataStorageInfo> findByParent(Integer id) {
		List<DataStorageInfo> users = repo.findByParent(id);
		return users;
	}

	@Override
	public DataStorageInfo create(DataStorageInfo t) {
		t.setId(null);
		t.setLast_time(new Date());
		return repo.save(t);
	}

	@Override
	public DataStorageInfo update(DataStorageInfo t) {
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		return;
	}

}
