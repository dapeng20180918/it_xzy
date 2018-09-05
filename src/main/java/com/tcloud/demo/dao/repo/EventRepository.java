package com.tcloud.demo.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcloud.demo.model.Event;


public interface EventRepository extends JpaRepository<Event,Integer>{
	List<Event> findByOperatorLike(String name);

}
