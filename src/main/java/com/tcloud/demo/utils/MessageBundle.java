package com.tcloud.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageBundle {
	private static MessageSource messageSource;

	public static String USER_LOGIN = "USER_LOGIN";
	public static String USER_LOGOUT = "USER_LOGOUT";
	public static String USER_SSOLOGIN = "USER_SSOLOGIN";
	
	public static String ROLE_CREATE = "ROLE_CREATE";
	public static String ROLE_UPDATE = "ROLE_UPDATE";
	public static String ROLE_DELETE = "ROLE_DELETE";
	
	public static String USER_CREATE = "USER_CREATE";
	public static String USER_UPDATE = "USER_UPDATE";
	public static String USER_DELETE = "USER_DELETE";
	
	public static String DATA_SOURCE_CREATE = "DATA_SOURCE_CREATE";
	public static String DATA_SOURCE_UPDATE = "DATA_SOURCE_UPDATE";
	public static String DATA_SOURCE_DELETE = "DATA_SOURCE_DELETE";
	
	public static String DATA_COLLECTOR_CREATE = "DATA_COLLECTOR_CREATE";
	public static String DATA_COLLECTOR_UPDATE = "DATA_COLLECTOR_UPDATE";
	public static String DATA_COLLECTOR_DELETE = "DATA_COLLECTOR_DELETE";
	
	public static String DATA_STORAGE_INFO_CREATE = "DATA_STORAGE_INFO_CREATE";
	
	public static String DATA_COLLECT_SCHEDULE_CREATE = "DATA_COLLECT_SCHEDULE_CREATE";
	public static String DATA_COLLECT_SCHEDULE_UPDATE = "DATA_COLLECT_SCHEDULE_UPDATE";
	public static String DATA_COLLECT_SCHEDULE_DELETE = "DATA_COLLECT_SCHEDULE_DELETE";
	
	public static String ANALYZE_ADAPTER_CREATE = "ANALYZE_ADAPTER_CREATE";
	public static String ANALYZE_ADAPTER_UPDATE = "ANALYZE_ADAPTER_UPDATE";
	public static String ANALYZE_ADAPTER_DELETE = "ANALYZE_ADAPTER_DELETE";
	
	public static String ANALYZE_STRATEGY_CREATE = "ANALYZE_STRATEGY_CREATE";
	public static String ANALYZE_STRATEGY_UPDATE = "ANALYZE_STRATEGY_UPDATE";
	public static String ANALYZE_STRATEGY_DELETE = "ANALYZE_STRATEGY_DELETE";
	
	public static String ANALYZE_TASK_CREATE = "ANALYZE_TASK_CREATE";
	public static String ANALYZE_TASK_UPDATE = "ANALYZE_TASK_UPDATE";
	public static String ANALYZE_TASK_DELETE = "ANALYZE_TASK_DELETE";
	
	public static String ANALYZE_SETTING_UPDATE = "ANALYZE_SETTING_UPDATE";
	
	@Autowired
	public void setMessageSource(MessageSource messageSource){
		MessageBundle.messageSource = messageSource;
	}
	
	public static String getMsg(String key){
		try{
			String msg = messageSource.getMessage(key, null, null);
			return msg;
		}catch(Exception e){
			return key;
		}
	}
	
}
