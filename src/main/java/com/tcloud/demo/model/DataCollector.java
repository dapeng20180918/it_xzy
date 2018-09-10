package com.tcloud.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Proxy(lazy = false)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class DataCollector {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, unique = true)
	private String name;
	private Integer datasource_id; // FK
	private String user_name; // User.name
	@Column(updatable = false)
	private Date create_date;
	private String description;

	private String jar_name;
	private String jar_params; // "n1:p1;n2:p2;..." n:param name, p:default value
	
	private String tree_name; // json: lv1->lv2
	private int tree_id;
	private String storage_type;
	private String storage_location;
	
	public int getTree_id() {
		return tree_id;
	}

	public void setTree_id(int tree_id) {
		this.tree_id = tree_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDatasource_id() {
		return datasource_id;
	}

	public void setDatasource_id(Integer datasource_id) {
		this.datasource_id = datasource_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJar_name() {
		return jar_name;
	}

	public void setJar_name(String jar_name) {
		this.jar_name = jar_name;
	}

	public String getJar_params() {
		return jar_params;
	}

	public void setJar_params(String jar_params) {
		this.jar_params = jar_params;
	}

	public String getTree_name() {
		return tree_name;
	}

	public void setTree_name(String tree_name) {
		this.tree_name = tree_name;
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

	public DataCollector() {
		super();
	}

}
