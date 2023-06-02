package com.codewithdurgesh.blog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.InvalidCredentialException;
import com.codewithdurgesh.blog.payloads.JwtAuthRequest;
import com.codewithdurgesh.blog.payloads.JwtAuthResponse;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.repositories.UserRepository;
import com.codewithdurgesh.blog.security.JwtTokenHelper;
import com.codewithdurgesh.blog.services.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth/")
@Slf4j
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * @Author Ekta
	 * @apiNote This is Login api to generate token
	 * @param request
	 * @return JwtAuthResponse
	 * @throws Exception
	 */
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        log.info("Entering request for logging to create token");
		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
        
		User user = userRepo.findByEmail(request.getUsername()).get();
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(userDto);
		log.info("Completing request for logging to create token");
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid details !!");
			throw new InvalidCredentialException("Invalid Password");
		}
	}
	
	/**
	 * @Author Ekta
	 * @apiNote This is the method for registering User
	 * @param userDto
	 * @return UserDto
	 */
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		log.info("Entering request for registering User");
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		log.info("Completed request for registering User");
		return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
	}
}
