package com.codewithdurgesh.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.Comment;
import com.codewithdurgesh.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostDto {
	
	private Long postId;
    
	@NotBlank(message = "Title should not be blank")
	@Size(min=3, max=15, message="Title must be of min 3 chars & max of 10 chars")
	private String title;

	@NotBlank
	private String content;
    
	@NotEmpty
	private String imageName;
    
	
	private Date addedDate;

	private CategoryDto category;

	private UserDto user;
	
	private Set<CommentDto> comments=new HashSet<>();
}
