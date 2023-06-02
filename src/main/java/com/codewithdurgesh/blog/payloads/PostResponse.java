package com.codewithdurgesh.blog.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class PostResponse {

	private List<PostDto> content;
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private Long totalElements;
	
	private Integer totalPages;
	
	private Boolean lastPage;
	
	
}
