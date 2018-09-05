package com.tcloud.demo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.UserRepository;
import com.tcloud.demo.model.User;

@Repository
public class UserDao implements DaoTemplate<User>{
	@Autowired
	public UserRepository repo;
	
	public User findByNameLike(String name){
		return repo.findByNameLike(name);
	}
	
	@Override
	public List<User> getAll(){
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		List<User>  users = repo.findAll(sort);
		return users;
	}
	
	public User getOne(Integer id){
		return repo.getOne(id);
	}

	@Override
	public User create(User t) {
		t.setId(null);
		t.setCreate_date(new Date());
		t.setStatus(1);
		return repo.save(t);
	}

	@Override
	public User update(User t) {
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}


}
