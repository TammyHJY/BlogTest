package com.blog.demo.service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.blog.demo.dto.PostDto;
import com.blog.demo.entity.Post;
import com.blog.demo.repository.PostRepository;
import com.blog.demo.service.PostService;

@Service //這個註解只是標註該類處於業務邏輯層
public class PostServiceImpl implements PostService{
	
	
	private PostRepository postRepository;
	
	private ModelMapper modelmapper;
	

	public PostServiceImpl(PostRepository postRepository, ModelMapper modelmapper) {
		super();
		this.postRepository = postRepository;
		this.modelmapper = modelmapper;
	}

	@Override
	public PostDto creatPost(PostDto postDto) {
		
		//convert DTO to entity (client to DB)
		Post post = mapToEntity(postDto);
		Post newPost = postRepository.save(post);
		
		//convert entity to DTO (DB to client)
		PostDto postResponse = mapToDTO(newPost);				
		return postResponse;
	}

	@Override
	public PostDto getPost(Long id) {
		Post post = postRepository.findById(id).orElse(null);
		return mapToDTO(post);
	}

	@Override
	public PostDto updatePost(Long id, PostDto postDto) {
		// get post by id from DB
		Post post = postRepository.findById(id).orElse(null);
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post updatedPost = postRepository.save(post);
		return mapToDTO(updatedPost);
	}

	@Override
	public void deleteById(Long id) {

		Post post = postRepository.findById(id).orElse(null);
		postRepository.delete(post);
		
	}
	
	
	//convert DTO to entity (client to DB)
	private Post mapToEntity(PostDto postDto) {
		/*原本應該是一個一個setting....
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		*/
		
		//可以用modelmapper 直接mapping! 
		Post post = modelmapper.map(postDto, Post.class);//將postDto 的數據轉給Post entity
		return post;				
	}
	
	//convert entity to DTO (DB to client)
	private PostDto mapToDTO(Post post) {
		PostDto postDto = modelmapper.map(post, PostDto.class);
		return postDto;	
	}

}
