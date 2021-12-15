package com.blog.demo.dto; 

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "部落格貼文")

public class PostDto {
	
	@ApiModelProperty(value = "pk") //Swagger UI 上看到的註解
	private long id;
	
	@ApiModelProperty(value = "標題", required = true)
	private String title;
	
	@ApiModelProperty(value = "描述")
	private String description;
	
	@ApiModelProperty(value = "內容", required = true)
	private String content;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
