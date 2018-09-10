package com.tcloud.demo.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tcloud.demo.model.DataCollectSchedule;


public interface DataCollectScheduleRepository extends JpaRepository<DataCollectSchedule,Integer>{
	@Query(value = "select * from data_collect_schedule where status=1 ", nativeQuery = true)
	@Modifying
	public List<DataCollectSchedule> getRunningSchedules();
}
