package com.blog.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "註冊資訊")
public class SignUpDto {
	
	@ApiModelProperty(value = "使用者名稱", required = true) //Swagger UI 上看到的註解
	private String name;
	
	@ApiModelProperty(value = "帳號", required = true) 
	private String username;
	
	@ApiModelProperty(value = "信箱", required = true) 
	private String email;
	
	@ApiModelProperty(value = "密碼", required = true) 
	private String password;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
