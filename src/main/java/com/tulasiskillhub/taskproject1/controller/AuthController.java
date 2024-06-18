package com.tulasiskillhub.taskproject1.controller;

//import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tulasiskillhub.taskproject1.payload.JWTAuthResponse;
import com.tulasiskillhub.taskproject1.payload.LoginDto;
import com.tulasiskillhub.taskproject1.payload.UserDto;
import com.tulasiskillhub.taskproject1.security.JwtTokenProvider;
import com.tulasiskillhub.taskproject1.service.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired(required = true)
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	//POST store the user in DB
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		
		return new ResponseEntity<>(userService.createUser(userDto),HttpStatus.CREATED);
		
		
	}
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> loginUser(@RequestBody LoginDto loginDto){
		
		Authentication authentication=
				authenticationManager.authenticate(
						
						new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
						);
		System.out.println(authentication);
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	String token=jwtTokenProvider.generateToken(authentication);//get the token
	
	return ResponseEntity.ok(new JWTAuthResponse(token));
	}
	
	

}
