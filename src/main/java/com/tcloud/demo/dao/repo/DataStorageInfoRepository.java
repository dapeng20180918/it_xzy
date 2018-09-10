package com.tcloud.demo.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tcloud.demo.model.DataStorageInfo;

public interface DataStorageInfoRepository extends JpaRepository<DataStorageInfo, Integer> {
	List<DataStorageInfo> findByParent(Integer id);

//	@Query(value = "delete from data_storage_info where id=?1 ", nativeQuery = true)
//	@Modifying
//	public void deleteById(int id);
	
	@Query(value = "select * from data_storage_info where storage_location like ?1", nativeQuery = true)
	public DataStorageInfo findByName(String name);

}
