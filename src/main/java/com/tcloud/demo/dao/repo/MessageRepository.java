package com.tcloud.demo.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tcloud.demo.model.Message;


public interface MessageRepository extends JpaRepository<Message,Integer>{
	List<Message> findByOperatorAndReaded(String name, Boolean readed);

	@Transactional
	@Query(value = "update message set readed=1 where operator like ?1", nativeQuery = true)
	@Modifying
	void readMessages(String name);
}
