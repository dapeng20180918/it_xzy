package com.tcloud.demo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.AnalyzeStrategyRepository;
import com.tcloud.demo.model.AnalyzeStrategy;

@Repository
public class AnalyzeStrategyDao implements DaoTemplate<AnalyzeStrategy> {
	@Autowired
	public AnalyzeStrategyRepository repo;

	@Override
	public List<AnalyzeStrategy> getAll() {
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		List<AnalyzeStrategy> users = repo.findAll(sort);
		return users;
	}

	public AnalyzeStrategy getOne(Integer id){
		return repo.getOne(id);
	}
	
	public List<AnalyzeStrategy> findByType(String type){
		return repo.findByType(type);
	}
	
	@Override
	public AnalyzeStrategy create(AnalyzeStrategy t) {
		t.setId(null);
		t.setCreate_date(new Date());
		return repo.save(t);
	}

	@Override
	public AnalyzeStrategy update(AnalyzeStrategy t) {
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
