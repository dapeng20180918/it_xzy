package com.tcloud.demo.filter;

import com.tcloud.demo.exception.TokenException;
import com.tcloud.demo.utils.CookieUtils;

import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 自定义JWT认证过滤器 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

	private static final Logger logger = LoggerFactory
			.getLogger(JWTAuthenticationFilter.class);
	
	private String SIGNING_KEY = "";

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String SIGNING_KEY) {
		super(authenticationManager);
		this.SIGNING_KEY = SIGNING_KEY;
		logger.info("SIGNING_KEY = {}", SIGNING_KEY);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		String header = request.getHeader("Authorization");
//		System.out.println(request.getRequestURI());
		String token = CookieUtils.getCookie(request, "Authorization");
		if (token == null || !token.startsWith("Bearer-") || request.getRequestURI().indexOf("/rest/")!=0) {
			chain.doFilter(request, response);
			return;
		}
		try {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			response.setStatus(403);
			return;
		}
		
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(
			HttpServletRequest request, HttpServletResponse response) {
		String token = CookieUtils.getCookie(request, "Authorization");
//		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty()) {
			logger.error("Token is null");
			throw new TokenException("Token is null");
		}
		// parse the token.
		String user = null;
		try {
			user = Jwts.parser().setSigningKey(SIGNING_KEY)
					.parseClaimsJws(token.replace("Bearer-", "")).getBody()
					.getSubject();

			if (user != null) {
				ArrayList<GrantedAuthority> authorities = new ArrayList<>();
				return new UsernamePasswordAuthenticationToken(user, null,
						authorities);
			}
		} catch (Exception e) {
			logger.error("Token Error: {} ", e.getMessage());
			throw new TokenException("Token Error: " + e.getMessage());
		}
		return null;
	}

}
