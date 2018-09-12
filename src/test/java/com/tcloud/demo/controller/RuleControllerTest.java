package com.tcloud.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 


import java.util.HashMap;
import java.util.Map;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
 



import com.tcloud.demo.model.Role;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleControllerTest {
	@Autowired  
    private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@Before 
    public void setup() {  
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();  
    }  
  
//    @Test  
    public void testGetRules() throws Exception {  
    	Map<String, Object> map = new HashMap<>();
    	
        MvcResult result = mockMvc.perform(get("http://localhost:8088/rest/rules").content(JSONObject.toJSONString(map)))
        		.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))  
                .andReturn();
          
        System.out.println(result.getResponse().getContentAsString());  
    }  
	
//    @Test  
    public void testGetRoles() throws Exception {  
    	Map<String, Object> map = new HashMap<>();
    	
        MvcResult result = mockMvc.perform(get("http://localhost:8088/rest/role")
        		.content(JSONObject.toJSONString(map)))
        		.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))  
                .andReturn();
          
        System.out.println(result.getResponse().getContentAsString());  
    } 
	
//    @Test  
    public void testCreateRoles() throws Exception {
    	Role role = new Role();
    	role.setId(0);
    	role.setName("测试角色");
    	role.setDescription("test desc");
    	role.setCreate_date(null);
    	role.setRule_str("1;2;3");
    	System.out.println(JSON.toJSONString(role));
    	Map<String, Object> map = new HashMap<>();
    	
        MvcResult result = mockMvc.perform(post("http://localhost:8088/rest/role?id=0")
        		.content(JSONObject.toJSONString(role)).contentType(MediaType.APPLICATION_JSON)
        		)
        		.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))  
                .andReturn();
          
        System.out.println(result.getResponse().getContentAsString());  
    } 	
    
//    @Test  
    public void testUpdateRoles() throws Exception {
    	Role role = new Role();
    	role.setId(4);
    	role.setName("测试角色123");
    	role.setDescription("test desc 123");
    	role.setCreate_date(null);
    	role.setRule_str("1");
    	System.out.println(JSON.toJSONString(role));
    	Map<String, Object> map = new HashMap<>();
    	
        MvcResult result = mockMvc.perform(post("http://localhost:8088/rest/role?id=4")
        		.content(JSONObject.toJSONString(role)).contentType(MediaType.APPLICATION_JSON)
        		)
        		.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))  
                .andReturn();
          
        System.out.println(result.getResponse().getContentAsString());  
    } 	
	
//    @Test  
	public void testDeleteRoles() throws Exception {
		Map<String, Object> map = new HashMap<>();

		MvcResult result = mockMvc
				.perform(delete("http://localhost:8088/rest/role?id=1").content(
								JSONObject.toJSONString(map)))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
	}
	
	
	
	
	
}
