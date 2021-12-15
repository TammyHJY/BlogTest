package com.blog.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.demo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	//客製化方法找身分的類型名
	Optional<Role> findByName(String name);
}
