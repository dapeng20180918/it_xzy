package com.tcloud.demo.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tcloud.demo.model.AnalyzeTask;


public interface AnalyzeTaskRepository extends JpaRepository<AnalyzeTask,Integer>{
	@Query(value = "select * from analyze_task where task_type like ?1 and user_name like ?2 order by id", nativeQuery = true)
	@Modifying
	public List<AnalyzeTask> findByType(String type, String name);
	
	
	
	@Query(value = "select * from analyze_task where status=1 and create_date<=DATE_SUB(NOW(),INTERVAL ?1 MINUTE) limit 1", nativeQuery = true)
	@Modifying
	public List<AnalyzeTask> findOneTask(int minute);
}
