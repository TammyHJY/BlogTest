package com.blog.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor


@Entity
//啟動ORM對應! 連結Post class 和posts table
//uniqueConstraints 用來保證欄位在資料表中的唯一性，約束資料表中的欄位不能有重複的資料
@Table(
		name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}) 

public class Post {
	
	
	@Id //表示pk
	@GeneratedValue(strategy = GenerationType.IDENTITY ) //pk的生成方式:自增主鍵
	@Column(name = "id") //JPA 對應表格欄位
	private Long id;
	
	
	@Column(name = "title")
	private String title;
	
	
	@Column(name = "description")
	private String description;
	
	
	@Column(name = "content")
	private String content;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
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
