package com.tcloud.demo.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tcloud.demo.model.DataSource;


public interface DataSourceRepository extends JpaRepository<DataSource,Integer>{

}
