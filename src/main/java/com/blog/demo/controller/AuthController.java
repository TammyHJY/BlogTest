package com.blog.demo.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.demo.dto.JWTAuthResponse;
import com.blog.demo.dto.LoginDto;
import com.blog.demo.dto.SignUpDto;
import com.blog.demo.entity.Role;
import com.blog.demo.entity.User;
import com.blog.demo.repository.RoleRepository;
import com.blog.demo.repository.UserRepository;
import com.blog.demo.security.JwtTokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Api(value = "signin and signup API")
@EnableSwagger2
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired //Processes an {@link Authentication} request.
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    
    @ApiOperation(value = "for signin")//swagger上的註解
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
		
    	//比對資料庫的帳密, 產生認證資料
    	Authentication authentication = authenticationManager
    			.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
    	//將認證資料存至 SecurityContextHolder
    	SecurityContextHolder.getContext().setAuthentication(authentication);
    	//return new ResponseEntity<>("Signin is successful!", HttpStatus.OK);//一開始測試登入時的回傳值
    	
    	//get token from tokenProvider
    	String token = jwtTokenProvider.generateToken(authentication); //將上面產生的認證資料轉給tokenProvider製造token
    	return ResponseEntity.ok(new JWTAuthResponse(token));
    	
    	
    	
    	/**
    	 * This constructor can be safely used by any code that wishes to create a
    	 * <code>UsernamePasswordAuthenticationToken</code>, as the {@link #isAuthenticated()}
    	 * will return <code>false</code>.
    	 *
    	 
    	public UsernamePasswordAuthenticationToken(Object principal, Object credentials) {
    		super(null);
    		this.principal = principal;
    		this.credentials = credentials;
    		setAuthenticated(false);
    	}
    	*/
 	
    }
    
    
    //register
    @ApiOperation(value = "for signup")
    @PostMapping("/signUp")
    public ResponseEntity<?> userRegister(@RequestBody SignUpDto signUpDto){
    	
    	// check if username exists in a DB
    	if(userRepository.existsByUsername(signUpDto.getUsername()))	
    		
    		return new ResponseEntity<>("This username is already taken!", HttpStatus.BAD_REQUEST);
    	// check if email exists in a DB
    	if(userRepository.existsByUsername(signUpDto.getEmail()))
    		return new ResponseEntity<>("This email is already taken!", HttpStatus.BAD_REQUEST);
    	
    	// create user object and set role
    	User user = new User();
    	user.setName(signUpDto.getName());
    	user.setUsername(signUpDto.getUsername());
    	user.setEmail(signUpDto.getEmail());
    	user.setPassword(passwordEncoder.encode(signUpDto.getPassword())); //存進db的密碼要編譯加密
    	
    	Role roles = roleRepository.findByName("ROLE_ADMIN").get(); //get() 是optional 的方法, 可以得到Role  
    	user.setRoles(Collections.singleton(roles)); //Collections.singleton(roles): Returns an immutable set containing only the specified object.
    	userRepository.save(user);
    	return new ResponseEntity<>("Registion successes!", HttpStatus.OK);
    }
    
    /* 在SQL SERVER 的roles table 執行以下命令先建資料以利測試
     INSERT INTO [dbo].[roles]([name]) VALUES ('ROLE_USER');
     INSERT INTO [dbo].[roles]([name]) VALUES ('ROLE_ADMIN');

     */
	
	

}
