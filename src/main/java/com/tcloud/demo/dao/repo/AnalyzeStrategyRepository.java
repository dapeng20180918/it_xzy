package com.tcloud.demo.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tcloud.demo.model.AnalyzeStrategy;


public interface AnalyzeStrategyRepository extends JpaRepository<AnalyzeStrategy,Integer>{
	@Query(value = "select * from analyze_strategy where class_type like ?1 order by id", nativeQuery = true)
	@Modifying
	public List<AnalyzeStrategy> findByType(String type);
}
