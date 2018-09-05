package com.tcloud.demo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.AnalyzeAdapterRepository;
import com.tcloud.demo.model.AnalyzeAdapter;

@Repository
public class AnalyzeAdapterDao implements DaoTemplate<AnalyzeAdapter> {
	@Autowired
	public AnalyzeAdapterRepository repo;

	@Override
	public List<AnalyzeAdapter> getAll() {
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		List<AnalyzeAdapter> users = repo.findAll(sort);
		return users;
	}

	public AnalyzeAdapter getOne(Integer id){
		return repo.getOne(id);
	}
	
	@Override
	public AnalyzeAdapter create(AnalyzeAdapter t) {
		t.setId(null);
		t.setCreate_date(new Date());
		return repo.save(t);
	}

	@Override
	public AnalyzeAdapter update(AnalyzeAdapter t) {
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
