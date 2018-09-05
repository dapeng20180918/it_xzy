package com.tcloud.demo.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tcloud.demo.model.AnalyzeSetting;


public interface AnalyzeSettingRepository extends JpaRepository<AnalyzeSetting,Integer>{
	
}
