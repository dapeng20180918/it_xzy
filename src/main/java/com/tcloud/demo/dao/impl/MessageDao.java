package com.tcloud.demo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.MessageRepository;
import com.tcloud.demo.model.Message;

@Repository
public class MessageDao implements DaoTemplate<Message> {
	@Autowired
	public MessageRepository repo;

	public List<Message> findByOperatorAndReaded(String name) {
		List<Message> messages = repo.findByOperatorAndReaded(name, false);
		return messages;
	}
	
	public void readMessages(String name){
		repo.readMessages(name);
	}
	
	@Override
	public List<Message> getAll() {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		List<Message> messages = repo.findAll(sort);
		return messages;
	}

	@Override
	public Message create(Message t) {
		t.setId(null);
//		t.setCreate_date(new Date());
		t.setReaded(false);
		return repo.save(t);
	}

	@Override
	public Message update(Message t) {
		return null;
	}

	@Override
	public void delete(Integer id) {
		return;
	}

}
