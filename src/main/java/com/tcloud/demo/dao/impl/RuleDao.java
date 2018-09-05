package com.tcloud.demo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.RuleRepository;
import com.tcloud.demo.model.Rule;

@Repository
public class RuleDao implements DaoTemplate<Rule>{
	@Autowired
	public RuleRepository repo;
	
	@Override
	public List<Rule> getAll(){
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		return repo.findAll(sort);
	}

	@Override
	public Rule create(Rule t) {
		return new Rule();
	}

	@Override
	public Rule update(Rule t) {
		return new Rule();
	}

	@Override
	public void delete(Integer id) {
		return;
	}


}
