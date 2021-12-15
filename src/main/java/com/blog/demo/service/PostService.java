package com.blog.demo.service;

import com.blog.demo.dto.PostDto;

public interface PostService {
	
	PostDto creatPost(PostDto postDto); 
	
	PostDto getPost(Long id); 
	
	PostDto updatePost(Long id, PostDto postDto);
	
	void deleteById(Long id);

}
