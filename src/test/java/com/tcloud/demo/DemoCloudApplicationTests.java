package com.tcloud.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tcloud.demo.dao.impl.RuleDao;
import com.tcloud.demo.model.Rule;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoCloudApplicationTests {
	@Autowired
	RuleDao dao;
	
//	@Test
	public void get(){
		List<Rule> rules = dao.getAll();
		System.out.println(rules);
	}
	
//	@Test
	public void contextLoads() {
		System.out.println("--------------------------start Test");
	}

}
