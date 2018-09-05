package com.tcloud.demo.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tcloud.demo.model.DataCollector;


public interface DataCollectorRepository extends JpaRepository<DataCollector,Integer>{

}
