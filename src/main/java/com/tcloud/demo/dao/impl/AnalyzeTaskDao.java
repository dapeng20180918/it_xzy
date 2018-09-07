package com.tcloud.demo.dao.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.AnalyzeTaskRepository;
import com.tcloud.demo.model.AnalyzeTask;

@Repository
public class AnalyzeTaskDao implements DaoTemplate<AnalyzeTask> {
	private static final Logger logger = LoggerFactory.getLogger(AnalyzeTaskDao.class);
	@Autowired
	public AnalyzeTaskRepository repo;

	@Value("${spring.sub.second}")
	public int subsecond;
	
	@Override
	public List<AnalyzeTask> getAll() {
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		List<AnalyzeTask> users = repo.findAll(sort);
		return users;
	}

	public List<AnalyzeTask> findByType(String type, String name) {
		return repo.findByType(type, name);
	}

	public AnalyzeTask updateOneTask(int minute) {
		List<AnalyzeTask> ats = repo.findOneTask(minute);
		if (ats.size() != 1) {
			return null;
		}
		
		AnalyzeTask at = ats.get(0);
		at.setStatus(2);
		
		int randomSecond = (int) (subsecond * Math.random());
		Calendar c = new GregorianCalendar();
		Date date = new Date();
		c.setTime(date);
		c.add(Calendar.SECOND, -randomSecond);
		date = c.getTime();
		at.setFinish_date(date);
		
		logger.info("Update Task Status, name:{}, finish time:{}", at.getName(), at.getFinish_date());
		repo.save(at);
		return at;
	}

	@Override
	public AnalyzeTask create(AnalyzeTask t) {
		t.setId(null);
		t.setCreate_date(new Date());
		t.setStatus(1);
		return repo.save(t);
	}

	@Override
	public AnalyzeTask update(AnalyzeTask t) {
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
