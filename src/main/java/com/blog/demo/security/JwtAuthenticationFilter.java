package com.blog.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// get JWT (token) from http request
		String token = getJWTfromRequest(request); //利用下面自製方法取得token
		
		//validate token : 利用JwtTokenProvider 內的方法驗證token, 進而取得存在token內的資訊 
		if(StringUtils.hasText(token) && tokenProvider.validateToken(token)) { 
			// get username from token
			String username = tokenProvider.getUsernameFromJWT(token);
			//load user associated with token
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities()
					);
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			// set spring security
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		 filterChain.doFilter(request, response);		
	}
	
	
	// Bearer <accessToken> 
	private String getJWTfromRequest(HttpServletRequest request) {	
		
		String bearerToken = request.getHeader("Authorization"); //取得存在SecurityContext的Authorization(有token資料)
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			//將token 前的Bearer 拿掉, 以利後續的比對(Bearer和token中間有一個空格, 所以數7)
			return bearerToken.substring(7, bearerToken.length()); 
		}	
		return null;		
	}
	
	
	

}
