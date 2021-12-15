package com.blog.demo.security;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.demo.entity.Role;
import com.blog.demo.entity.User;
import com.blog.demo.repository.UserRepository;

@Service //取得user在db的資料
public class CustomUserDetailsService implements UserDetailsService{
	
	
	private UserRepository userRepository;
	
	

	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//沒找到先設回傳null, 之後再改成回丟exception
		User user = userRepository.findByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException("User not found with username or email:" + username));
		//回傳db 內的資料(信箱, 密碼, 認證身分)
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	//認證身分利用有繼承GrantedAuthority的子類別SimpleGrantedAuthority
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
		
	}
}
