package com.blog.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.demo.dto.PostDto;
import com.blog.demo.service.PostService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Api(value = "CRUD for posts")
@RestController
@EnableSwagger2
public class PostController {
		
	@Autowired
	private PostService postService;
	

	@ApiOperation(value = "create a post")//swagger上的註解
	@PostMapping("/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
		/**
		 * Create a new {@code ResponseEntity} with the given body and status code, and no headers.
		 * @param body the entity body
		 * @param status the status code
		 */
		return new ResponseEntity<>(postService.creatPost(postDto), HttpStatus.CREATED); //返回postDto 和 201建構成功	
	}
	
	@ApiOperation(value = "find a post")//swagger上的註解
	@GetMapping("/posts/{id}")
	public ResponseEntity<PostDto> findPostById(@PathVariable long id){		
		/**
		 * A shortcut for creating a {@code ResponseEntity} with the given body
		 * and the status set to {@linkplain HttpStatus#OK OK}.
		 * @param body the body of the response entity (possibly empty)
		 * @return the created {@code ResponseEntity}
		 * */
		return ResponseEntity.ok(postService.getPost(id));	
	}

	@ApiOperation(value = "update a post")//swagger上的註解
	@PutMapping("/posts/{id}")
	public ResponseEntity<PostDto> updatePost(@PathVariable long id,
											  @RequestBody PostDto postDto){
		PostDto postResponse = postService.updatePost(id, postDto);
		
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
		
	}
	
	
	@ApiOperation(value = "delete a post")//swagger上的註解
	@DeleteMapping("/posts/{id}")
	public ResponseEntity<String> deletePost(@PathVariable long id){
		
		postService.deleteById(id);
		return new ResponseEntity<>("執行刪除成功", HttpStatus.OK);
	}
	
}
