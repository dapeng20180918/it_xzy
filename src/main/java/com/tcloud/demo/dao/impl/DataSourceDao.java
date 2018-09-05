package com.tcloud.demo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.DataSourceRepository;
import com.tcloud.demo.model.DataSource;

@Repository
public class DataSourceDao implements DaoTemplate<DataSource> {
	@Autowired
	public DataSourceRepository repo;

	@Override
	public List<DataSource> getAll() {
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		List<DataSource> users = repo.findAll(sort);
		return users;
	}

	public DataSource getOne(Integer id){
		return repo.getOne(id);
	}
	
	@Override
	public DataSource create(DataSource t) {
		t.setId(null);
		t.setCreate_date(new Date());
		return repo.save(t);
	}

	@Override
	public DataSource update(DataSource t) {
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
