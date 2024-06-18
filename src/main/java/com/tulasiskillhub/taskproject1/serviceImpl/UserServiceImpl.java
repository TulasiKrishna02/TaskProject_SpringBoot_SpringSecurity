package com.tulasiskillhub.taskproject1.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tulasiskillhub.taskproject1.entity.Users;
import com.tulasiskillhub.taskproject1.payload.UserDto;
import com.tulasiskillhub.taskproject1.repository.UserRepository;
import com.tulasiskillhub.taskproject1.service.UserService;

@Service
public class UserServiceImpl implements UserService {
     
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	
	@Override
	public UserDto createUser(UserDto userDto) {
		//userDto is not an entity of Users
		
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Users user=userDtoToEntity(userDto);//converted userDto to Users
		Users savedUser=userRepository.save(user);
		return entityToUserDto(savedUser);
	}
	
	private Users userDtoToEntity(UserDto userDto) {
		
		Users users=new Users();
		users.setName(userDto.getName());
		users.setEmail(userDto.getEmail());
		users.setPassword(userDto.getPassword());
		
		return users;
		
	}
	
	private UserDto entityToUserDto(Users savedUser) {
		
		UserDto userDto=new UserDto();
		userDto.setId(savedUser.getId());
		userDto.setEmail(savedUser.getEmail());
		userDto.setPassword(savedUser.getPassword());
		userDto.setName(savedUser.getName());
		return userDto;
		
		
	}

}
