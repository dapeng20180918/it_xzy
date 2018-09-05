package com.tcloud.demo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.DataCollectorRepository;
import com.tcloud.demo.model.DataCollector;

@Repository
public class DataCollectorDao implements DaoTemplate<DataCollector> {
	@Autowired
	public DataCollectorRepository repo;

	@Override
	public List<DataCollector> getAll() {
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		List<DataCollector> users = repo.findAll(sort);
		return users;
	}

	public DataCollector getOne(Integer id){
		return repo.getOne(id);
	}
	
	@Override
	public DataCollector create(DataCollector t) {
		t.setId(null);
		t.setCreate_date(new Date());
		return repo.save(t);
	}

	@Override
	public DataCollector update(DataCollector t) {
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
