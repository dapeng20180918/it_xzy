package com.tcloud.demo.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tcloud.demo.model.Reply;


public interface ReplyRepository extends JpaRepository<Reply,Integer>{
	List<Reply> findByOperatorLike(String name);

	@Query(value = "select * from reply where toplic_id=?1 order by id", nativeQuery = true)
	@Modifying
	List<Reply> getByTopic(int id);
}
