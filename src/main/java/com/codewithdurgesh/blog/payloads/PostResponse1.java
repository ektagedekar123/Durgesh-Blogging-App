package com.codewithdurgesh.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PostResponse1 {

	private List<PostDto> content;

	private Integer pageNo;

	private Integer pageSize;

	private int totalElements;

	
}
