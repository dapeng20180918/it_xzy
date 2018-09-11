package com.tcloud.demo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.FeedbackRepository;
import com.tcloud.demo.model.Feedback;

@Repository
public class FeedbackDao implements DaoTemplate<Feedback> {
	@Autowired
	public FeedbackRepository repo;

	public List<Feedback> findByOperatorLike(String name) {
		List<Feedback> Feedbacks = repo.findByOperatorLike(name);
		return Feedbacks;
	}
	
	@Override
	public List<Feedback> getAll() {
		Sort sort = new Sort(Sort.Direction.DESC, "lastDate");
		List<Feedback> Feedbacks = repo.findAll(sort);
		return Feedbacks;
	}

	@Override
	public Feedback create(Feedback t) {
		t.setId(null);
		t.setCreate_date(new Date());
		t.setLastDate(t.getCreate_date());
		return repo.save(t);
	}

	@Override
	public Feedback update(Feedback t) {
		return null;
	}
	
	public void addCount(Feedback t){
		repo.addCount(t.getId());
	}

	@Override
	public void delete(Integer id) {
		return;
	}

}
