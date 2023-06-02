package com.codewithdurgesh.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryResponse {

    private List<CategoryDto> content;
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private Long totalElements;
	
	private Integer totalPages;
	
	private Boolean lastPage;
	
}
