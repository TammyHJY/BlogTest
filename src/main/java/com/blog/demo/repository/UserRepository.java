package com.blog.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.demo.entity.User;



public interface UserRepository extends JpaRepository<User, Long>{

	//需客製化方法來搜尋email 或username 來找user
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
	Optional<User> findByUsernameOrEmail(String username, String email);
	//確認username跟email 是否已經存在
	Boolean existsByEmail(String email);
	Boolean existsByUsername(String username);

	
	
}
