package com.blog.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blog.demo.security.CustomUserDetailsService;
import com.blog.demo.security.JwtAuthenticationEntryPoint;
import com.blog.demo.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//此為基本安全認證, 撰寫哪個請求要認證
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	

	protected void configure(HttpSecurity http) throws Exception{
		
		/**
		 * Default configurations which will secure all the requests
		 */

	
//		  http
//		  	.csrf().disable()
//		 	.authorizeRequests() 
//		 		.anyRequest().authenticated() 
//		 		.and()
//		  	.formLogin().and() 
//		  	.httpBasic();
		 
		
		/**
		 * Custom configurations as per your requirement
		 */

		
//		   http 
//		   .csrf().disable()
//		   .authorizeRequests() 
//		   		//.antMatchers(HttpMethod.GET, "/**").permitAll()
//		   		.antMatchers("/swagger-ui/**").permitAll()
//		   		.antMatchers("/swagger-ui.html").permitAll()
//		   		//.antMatchers("/signin").permitAll()
//		   		.anyRequest().authenticated() 
//		   		.and() 
//		   .formLogin().and() 
//		   .httpBasic();
		
		
		
		/**
		 * Configuration to deny all the requests
		 */

		/*
		 * http 
		 	.authorizeRequests() 
		 		.anyRequest().denyAll() 
		 		.and() 
		 	.formLogin().and()
		  	.httpBasic();
		 */

		/**
		 * Configuration to permit all the requests
		 */

//		http 
//			.csrf().disable()
//			.authorizeRequests() 
//				.anyRequest().permitAll()
//				.and() 
//			.formLogin().and()
//			.httpBasic();
		   
		   

			/**
			 * Configuration for JWT
			 */
		   
		 http 
		   .csrf().disable()
		   .exceptionHandling() //將自製的jwt exception 導入, 有runtime 錯誤時可以回報
		   		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		   		.and()
		   .sessionManagement() //要用jwt, 所以關閉session 認證
		   		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		   		.and()		   
		   .authorizeRequests() //設定要驗證的請求
		   		.antMatchers(HttpMethod.GET, "/**").permitAll()
		   		.antMatchers("/auth/**").permitAll()
		   		.antMatchers("/swagger-ui.html#").permitAll()
		   		.antMatchers("/swagger-ui/**").permitAll()
		   		.antMatchers("/swagger-resources/**").permitAll()	   		
		   		.anyRequest().authenticated();
		  //將JWT filter 加在basicAuthentication 帳號密碼認證前 
		 http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		 
		

		   
		   
	}
	
	


//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		auth.inMemoryAuthentication()
//		.withUser("administrator").password("123456").authorities("administrator").and()
//		.withUser("guest").password("654321").authorities("guest").and()
//		.passwordEncoder(NoOpPasswordEncoder.getInstance());				
//	}		
	
	
//	@Override //先使用這個方式signin 進去signUp 
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//		UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("123456")).authorities("admin").build();
//		UserDetails gu = User.withUsername("guest").password(passwordEncoder().encode("123456")).authorities("guest").build();
//		userDetailsService.createUser(admin);
//		userDetailsService.createUser(gu);
//		auth.userDetailsService(userDetailsService);		
//	}
	
	
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
//		
//	}
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	
	@Override //客製帳密,並加密:呼叫 passwordEncoder()回傳一個PasswordEncoder object
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
           
    }


	@Override
	@Bean //給AuthController 去執行signin 確認, 來得認證資料存在SecurityContext
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// authenticationManagerBean(): 是WebSecurityConfigurerAdapter的方法, 會回傳AuthenticationManager
		return super.authenticationManagerBean(); 
	}
	 
	
	
	
}
