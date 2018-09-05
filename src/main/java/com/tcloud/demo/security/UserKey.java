package com.tcloud.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserKey {
	@Value("${spring.signing.key}")
	public String SIGNING_KEY;
	
}
