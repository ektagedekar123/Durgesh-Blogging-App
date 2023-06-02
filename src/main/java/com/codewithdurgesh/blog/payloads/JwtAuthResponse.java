package com.codewithdurgesh.blog.payloads;

import com.codewithdurgesh.blog.entities.User;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	
	private UserDto user;
}
