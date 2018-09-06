package com.tcloud.demo.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcloud.demo.model.Feedback;


public interface FeedbackRepository extends JpaRepository<Feedback,Integer>{
	List<Feedback> findByOperatorLike(String name);

}
