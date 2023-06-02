package com.codewithdurgesh.blog.services;

import com.codewithdurgesh.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Long postId, Long userId);
	
	void deleteComment(Long commentId);
}
