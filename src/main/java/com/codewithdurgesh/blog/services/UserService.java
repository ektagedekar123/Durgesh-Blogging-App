package com.codewithdurgesh.blog.services;

import java.util.List;



import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.payloads.UserResponse;

public interface UserService {
	
	
	UserDto registerNewUser(UserDto userDto);
	
	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto, Long userId);
	
	UserDto getUserById(Long id);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Long userId);
	
	public UserResponse getAllUserResponse(Integer pageNumber,Integer pageSize);

}
