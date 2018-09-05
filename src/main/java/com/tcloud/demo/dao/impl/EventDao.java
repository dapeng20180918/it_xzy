package com.tcloud.demo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.EventRepository;
import com.tcloud.demo.model.Event;

@Repository
public class EventDao implements DaoTemplate<Event> {
	@Autowired
	public EventRepository repo;

	public List<Event> findByOperatorLike(String name) {
		List<Event> events = repo.findByOperatorLike(name);
		return events;
	}
	
	@Override
	public List<Event> getAll() {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		List<Event> events = repo.findAll(sort);
		return events;
	}

	@Override
	public Event create(Event t) {
		t.setId(null);
		t.setCreate_date(new Date());
		return repo.save(t);
	}

	@Override
	public Event update(Event t) {
		return null;
	}

	@Override
	public void delete(Integer id) {
		return;
	}

}
