package com.blog.demo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		/*
		 *  This method is called whenever an exception is thrown due to an unauthenticated user
		 *  trying to access a resource that requires authentication  
		 */
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()); //SC_UNAUTHORIZED: 回傳401
	}

	
	
}
