package com.blog.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "登入資訊")
public class LoginDto {
	
	@ApiModelProperty(value = "使用者名稱", required = true) //Swagger UI 上看到的註解
	private String usernameOrEmail;
	
	@ApiModelProperty(value = "密碼", required = true)
	private String password;
	
	
	
	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}
	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
