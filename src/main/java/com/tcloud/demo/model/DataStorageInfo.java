package com.tcloud.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//data tree from a json file
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class DataStorageInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// private String name;
	private Integer parent; // from json
	private Long data_size; // default MB

	private Date last_time;
	private String storage_type;
	private String storage_location;// name

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Long getData_size() {
		return data_size;
	}

	public void setData_size(Long data_size) {
		this.data_size = data_size;
	}

	public Date getLast_time() {
		return last_time;
	}

	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}

	public String getStorage_type() {
		return storage_type;
	}

	public void setStorage_type(String storage_type) {
		this.storage_type = storage_type;
	}

	public String getStorage_location() {
		return storage_location;
	}

	public void setStorage_location(String storage_location) {
		this.storage_location = storage_location;
	}

	public DataStorageInfo() {
		super();
	}

}
