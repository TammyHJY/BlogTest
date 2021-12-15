package com.blog.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "JWT 資訊")
public class JWTAuthResponse {

	@ApiModelProperty(value = "產生的JWT") 
	private String accessToken;
	
	@ApiModelProperty(value = "JWT 類型") 
    private String tokenType = "Bearer";
	
    public JWTAuthResponse(String accessToken) {
		this.accessToken = accessToken;
	}

    
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
    
    
    
}
