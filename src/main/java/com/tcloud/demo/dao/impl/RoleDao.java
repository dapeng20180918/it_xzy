package com.tcloud.demo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.RoleRepository;
import com.tcloud.demo.model.Role;

@Repository
public class RoleDao implements DaoTemplate<Role>{
	@Autowired
	public RoleRepository repo;
	
	@Override
	public List<Role> getAll(){
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		return repo.findAll(sort);
	}
	
	public Role getOne(Integer id){
		return repo.getOne(id);
	}

	@Override
	public Role create(Role t) {
		t.setId(null);
		t.setCreate_date(new Date());
		return repo.save(t);
	}

	@Override
	public Role update(Role t) {
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}


}
