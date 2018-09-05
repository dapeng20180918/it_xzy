package com.tcloud.demo.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcloud.demo.model.User;


public interface UserRepository extends JpaRepository<User,Integer>{
	User findByNameLike(String name);
}
