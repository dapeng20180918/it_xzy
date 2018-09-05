package com.tcloud.demo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tcloud.demo.dao.DaoTemplate;
import com.tcloud.demo.dao.repo.AnalyzeSettingRepository;
import com.tcloud.demo.model.AnalyzeSetting;

@Repository
public class AnalyzeSettingDao implements DaoTemplate<AnalyzeSetting> {
	@Autowired
	public AnalyzeSettingRepository repo;

	@Override
	public List<AnalyzeSetting> getAll() {
		List<AnalyzeSetting> users = repo.findAll();
		return users;
	}

	@Override
	public AnalyzeSetting create(AnalyzeSetting t) {
		return null;
	}

	@Override
	public AnalyzeSetting update(AnalyzeSetting t) {
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		return;
	}

}
