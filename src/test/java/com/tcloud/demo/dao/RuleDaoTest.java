package com.tcloud.demo.dao;


import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.tcloud.demo.dao.impl.RuleDao;
import com.tcloud.demo.model.Rule;


//@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuleDaoTest.class)
@ComponentScan(basePackages="com.tcloud.demo")  
public class RuleDaoTest {
	@Autowired
	RuleDao dao;
	
//	@Test
	public void get(){
		System.out.println(dao.getAll());
	}
	
//	@Test
	public void create(){
		Rule rule = new Rule();
		rule.setName("test");
		rule.setDescription("testdesc");
		dao.create(rule);
	}
	
//	@Test
	public void update(){
		Rule rule = new Rule();
		rule.setId(5);
		rule.setName("test55");
		rule.setDescription("testdesc55");
		dao.update(rule);
	}
	
//	@Test
	public void delete(){
		dao.delete(5);
	}
}
