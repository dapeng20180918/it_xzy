package com.tcloud.demo.dao.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tcloud.demo.model.Feedback;


public interface FeedbackRepository extends JpaRepository<Feedback,Integer>{
	List<Feedback> findByOperatorLike(String name);
	
	@Transactional
	@Query(value = "update feedback set last_replyer=?2,last_date=?3 where id=?1", nativeQuery = true)
	@Modifying
	void updateOne(int id, String last_replyer, Date last_date);
	
	@Transactional
	@Query(value = "update feedback set click_count=click_count+1 where id=?1", nativeQuery = true)
	@Modifying
	void addCount(int id);

}
