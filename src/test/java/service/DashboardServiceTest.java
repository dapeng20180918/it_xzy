package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.tcloud.demo.dao.DashboardService;
import com.tcloud.demo.model.Dashboard;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DashboardServiceTest.class)
@ComponentScan(basePackages="com.tcloud.demo")
public class DashboardServiceTest {
	@Autowired
	DashboardService ds;
	
	@Test
	public void get(){
		System.out.println("------start------");
		Dashboard a = ds.getDashboard();
		System.out.println(a);
		System.out.println("------end------");
	}
}
