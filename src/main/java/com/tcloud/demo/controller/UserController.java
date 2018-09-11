package com.tcloud.demo.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.mysql.jdbc.StringUtils;
import com.tcloud.demo.dao.impl.EventDao;
import com.tcloud.demo.dao.impl.RoleDao;
import com.tcloud.demo.dao.impl.UserDao;
import com.tcloud.demo.model.Response;
import com.tcloud.demo.model.User;
import com.tcloud.demo.model.Event;
import com.tcloud.demo.security.UserKey;
import com.tcloud.demo.utils.MessageBundle;

@RestController
@RequestMapping("/rest")
public class UserController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserDao userDao;
	
	@Autowired
	EventDao eventDao;
	
	@Autowired
	RoleDao roleDao;
	
	@Value("${spring.token.expire}")
	private int expire;
	
	@Autowired
	UserKey key;
	
	//login
	@PostMapping(value = "/user/login")
	@ResponseBody
	public User login(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) {
		User userVo =  userDao.findByNameLike(user.getName());
		
		String ipAddr = request.getHeader("X-Real-IP");
		if(StringUtils.isNullOrEmpty(ipAddr))
			ipAddr = request.getRemoteAddr();
		
		if (userVo != null && userVo.getPassword().equalsIgnoreCase(user.getPassword())) {
			String subject = userVo.getName();
			String token = Jwts
					.builder()
					.setSubject(subject)
					.setExpiration(new Date(System.currentTimeMillis() + expire * 60 * 1000))
					.signWith(SignatureAlgorithm.HS512, key.SIGNING_KEY)
					.compact();
			// 登录成功后，返回token到header里面
			String val = "/rest/user/login，user:{}, ip:{}, expire:{} minutes ";
			logger.info(val, user.getName(), ipAddr, expire);
			logger.info("Authorization: {}", "Bearer-" + token);
			response.addHeader("Authorization", "Bearer-" + token);
			Event event = new Event();
			event.setType(MessageBundle.USER_LOGIN);
			event.setOperator(user.getName());
			event.setDescription(String.format("name:%s, ip:%s, expire:%d minutes", user.getName(), ipAddr, expire));
			eventDao.create(event);
			
		}else{
			String val = "/rest/user/login，user:{}, Unauthorized";
			logger.warn(val, user.getName());
			response.setStatus(401);
		}
		return userVo;
	}
	
	@PostMapping(value = "/user/loginadmin")
	@ResponseBody
	public User loginadmin(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) {
		User userVo =  userDao.findByNameLike(user.getName());
		String ipAddr = request.getLocalAddr();
		if (userVo != null && 
			userVo.getRole_id()==1 &&
			userVo.getPassword().equalsIgnoreCase(user.getPassword())) {
			String subject = userVo.getName();
			String token = Jwts
					.builder()
					.setSubject(subject)
					.setExpiration(new Date(System.currentTimeMillis() + expire * 60 * 1000))
					.signWith(SignatureAlgorithm.HS512, key.SIGNING_KEY)
					.compact();
			String val = "/rest/user/loginadmin，user:{}, ip:{}, expire:{} minutes ";
			logger.info(val, user.getName(), ipAddr, expire);
			logger.info("Authorization: {}", "Bearer-" + token);
			response.addHeader("Authorization", "Bearer-" + token);
			Event event = new Event();
			event.setType(MessageBundle.USER_LOGIN);
			event.setOperator(user.getName());
			event.setDescription(String.format("name:%s, ip:%s, expire:%d minutes", user.getName(), ipAddr, expire));
			eventDao.create(event);
		}else{
			String val = "/rest/user/loginadmin，user:{}, Unauthorized";
			logger.warn(val, user.getName());
			response.setStatus(401);
		}
		return userVo;
	}
	//SSO login
	@GetMapping(value = "/user/ssologin")
	@ResponseBody
	public User loginSSO(String token, HttpServletResponse response, HttpServletRequest request) {
		String ipAddr = request.getLocalAddr();
		String user = "";
		User userVo = new User();
		try {
			user = Jwts.parser().setSigningKey(key.SIGNING_KEY)
					.parseClaimsJws(token.replace("Bearer-", "")).getBody()
					.getSubject();
			
			//check user
			userVo = userDao.findByNameLike(user);
			String val = "/rest/user/loginSSO: user:{}, ip:{}" ;
			logger.info(val, user, ipAddr);
			Event event = new Event();
			event.setType(MessageBundle.USER_SSOLOGIN);
			event.setOperator(user);
			event.setDescription(String.format("name:%s, ip:%s", user, ipAddr));
			eventDao.create(event);
		} catch (Exception e) {
			String val = "/rest/user/loginSSO，Unauthorized";
			logger.warn(val);
			response.setStatus(401);
		}
		return userVo;
	}
	
	//SSO get key
//	@GetMapping(value = "/user/ssokey")
	@ResponseBody
	public void getSSOKey(@RequestBody User user) {
		String val = "/rest/user/getSSOKey:" + user.getName();
		logger.info(val);
	}
	
	//logout
	@DeleteMapping(value = "/user/logout")
	@ResponseBody
	public void logout() {
		String val = "/rest/user/logout:" + getUser();
		logger.info(val);
		Event event = new Event();
		event.setType(MessageBundle.USER_LOGOUT);
		event.setOperator(getUser());
		eventDao.create(event);
	}
	
	//gets
	@GetMapping(value = "/user")
	@ResponseBody
	public List<User> get() {
//		String myName = getUser();
//		String val = "/rest/user/getAll, operator:{}";
//		logger.info(val, myName);
		return userDao.getAll();
	}
	
	@GetMapping(value = "/user/{name}")
	@ResponseBody
	public User getByName(@PathVariable("name") String name) {
		User user = userDao.findByNameLike(name);
		String status = user.getStatus()==1?"激活":"冻结";
		String role = "";
		try {
			role = roleDao.getOne(user.getRole_id().intValue()).getName();
		} catch (Exception e) {
			// TODO: handle exception
		}
		user.setStatus_string(status);
		user.setRole_string(role);
		return user;
	}
	
	//create
	@PostMapping(value = "/user/create")
	@ResponseBody
	public User create(@RequestBody User user) {
		String myName = getUser();
		String val = "/rest/user/create:{}, operator:{}" ;
		logger.info(val, user.getName(), myName);
		Event event = new Event();
		event.setType(MessageBundle.USER_CREATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", user.getName()));
		eventDao.create(event);
		
		return userDao.create(user);
	}
	
	//update
	@PostMapping(value = "/user/update")
	@ResponseBody
	public User update(@RequestBody User user) {
		String val = "/rest/user/update:{}, id:{}, operator:{}";
		logger.info(val, user.getName(), user.getId(), getUser());
		Event event = new Event();
		event.setType(MessageBundle.USER_UPDATE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", user.getName()));
		eventDao.create(event);
		
		return userDao.update(user);
	}
	
	//delete
	@DeleteMapping(value = "/user/{id}")
	@ResponseBody
	public Response delete(@PathVariable("id") Integer id) {
		String val = "/rest/user/delete:{}, id:{}, operator:{}";
		User user = userDao.getOne(id);
		logger.info(val, user.getName(), id, getUser());
		userDao.delete(id);
		
		Event event = new Event();
		event.setType(MessageBundle.USER_DELETE);
		event.setOperator(getUser());
		event.setDescription(String.format("name:%s", user.getName()));
		eventDao.create(event);
		
		return new Response("success", "");
	}
}
