package com.tcloud.demo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.DataCollectScheduleRepository;
import com.tcloud.demo.model.DataCollectSchedule;

@Repository
public class DataCollectScheduleDao implements DaoTemplate<DataCollectSchedule> {
	@Autowired
	public DataCollectScheduleRepository repo;

	@Override
	public List<DataCollectSchedule> getAll() {
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		List<DataCollectSchedule> users = repo.findAll(sort);
		return users;
	}

	public DataCollectSchedule getOne(Integer id){
		return repo.getOne(id);
	}
	
	@Override
	public DataCollectSchedule create(DataCollectSchedule t) {
		t.setId(null);
		t.setCreate_time(new Date());
		t.setStatus(1);
		return repo.save(t);
	}

	@Override
	public DataCollectSchedule update(DataCollectSchedule t) {
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
