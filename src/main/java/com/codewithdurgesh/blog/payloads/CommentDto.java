package com.codewithdurgesh.blog.payloads;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

	private Long Id;
	
	private String content;
	
	private UserDto user;
	
}
