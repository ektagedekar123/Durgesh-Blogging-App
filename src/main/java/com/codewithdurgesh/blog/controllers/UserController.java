package com.codewithdurgesh.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.payloads.UserResponse;
import com.codewithdurgesh.blog.services.UserService;
import com.codewithdurgesh.blog.utils.AppConstants;

@RestController
@RequestMapping("/api")
public class UserController {
	
	
	Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * @author Ekta Gedekar
	 * @apiNote This method is for creating User
	 * @param userDto
	 * @return UserDto
	 */
	
	@PostMapping("/users")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		logger.info("Entering request for create user");
		UserDto dto = this.userService.createUser(userDto);
		logger.info("Completed request for create user");
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	
	/**
	 * @author Ekta
	 * @apiNote This method is for updating user
	 * @param userDto
	 * @param uid
	 * @return UserDto
	 */
	
	@PutMapping("/users/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Long uid){
		logger.info("Entering request for update user with user id:{}",uid);
		UserDto updateUser = this.userService.updateUser(userDto, uid);
		logger.info("Completed request for update user with user id:{}",uid);
		return ResponseEntity.ok(updateUser);
	}
	
	
	/**
	 * @author Ekta
	 * @apiNote This method is for getting single user
	 * @param uid
	 * @return UserDto
	 */
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Long uid){
		logger.info("Entering request for getting Single User with user id:{}",uid);
		UserDto userDto = this.userService.getUserById(uid);
		logger.info("Completed request for getting Single User with user id:{}",uid);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}
	
	
	/**
	 * @author Ekta
	 * @apiNote This method is for getting all users
	 * @return List<UserDto>
	 */
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUser(){
		logger.info("Entering request to get all users");
		List<UserDto> userDtos = this.userService.getAllUsers();
		logger.info("Completed request to get all users");
		return new ResponseEntity<>(userDtos, HttpStatus.OK);
	}
	
	
	/**
	 * @author Ekta
	 * @apiNote This method is for deleting user which is accessed by only ADMIN User
	 * @param uid
	 * @return ApiResponse
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/users/{uid}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long uid){
		logger.info("Entering request to delete user with user id:{}",uid);
		this.userService.deleteUser(uid);
		logger.info("Completed request to delete user with user id:{}",uid);
		return new ResponseEntity<>(new ApiResponse(AppConstants.USER_DELETE, true), HttpStatus.OK);
	}
	
	/**
	 * @author Ekta
	 * @This method is for getting all users in pages
	 * @param pageNumber
	 * @param pageSize
	 * @return UserResponse
	 */
	@GetMapping("/users/pages")
	public ResponseEntity<UserResponse> getAlluserResponse(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {

		logger.info("Request Entering to get All Records from User");

		logger.info("All records Get Successfully");
		return ResponseEntity.ok(this.userService.getAllUserResponse(pageNumber, pageSize));
	}
	

}
