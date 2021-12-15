package com.blog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.demo.entity.Post;


public interface PostRepository extends JpaRepository<Post, Long>{  //<class, pk的型別>

}
