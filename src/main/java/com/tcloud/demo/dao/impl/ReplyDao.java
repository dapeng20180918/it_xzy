package com.tcloud.demo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.FeedbackRepository;
import com.tcloud.demo.dao.repo.ReplyRepository;
import com.tcloud.demo.model.Reply;

@Repository
public class ReplyDao implements DaoTemplate<Reply> {
	@Autowired
	public ReplyRepository repo;
	
	@Autowired
	public FeedbackRepository feedbackRepo;
	
	@Override
	public List<Reply> getAll() {
		Sort sort = new Sort(Sort.Direction.DESC, "last_date");
		List<Reply> replys = repo.findAll(sort);
		return replys;
	}
	
	public List<Reply> getByTopic(int id) {
		List<Reply> replys = repo.getByTopic(id);
		return replys;
	}

	@Override
	public Reply create(Reply t) {
		t.setId(null);
		t.setCreate_date(new Date());
		Reply reply = repo.save(t);
		int reply_count = repo.getByTopic(t.getToplicId()).size();
		feedbackRepo.updateOne(reply.getToplicId(), reply.getOperator(), reply.getCreate_date(), reply_count);
		return reply;
	}

	@Override
	public Reply update(Reply t) {
		return null;
	}

	@Override
	public void delete(Integer id) {
		return;
	}

}
