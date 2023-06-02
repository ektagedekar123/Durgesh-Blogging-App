package com.codewithdurgesh.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

	private Long categoryId;
	
	@NotBlank
	@Size(min = 3, message="Minimum chars for Title is 3")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10, message="Minimum chars for description is 10" )
	private String categoryDescription;
}
