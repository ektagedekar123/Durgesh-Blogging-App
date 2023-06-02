package com.codewithdurgesh.blog.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.CommentDto;
import com.codewithdurgesh.blog.services.CommentService;
import com.codewithdurgesh.blog.utils.AppConstants;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	Logger logger = LoggerFactory.getLogger(CommentController.class);

	/**
	 * @author Ekta
	 * @apiNote This method is for creating comment
	 * @param commentDto
	 * @param postId,    userId
	 * @return CommentDto
	 */
	@PostMapping("/post/{postId}/user/{userId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Long postId,
			@PathVariable Long userId) {
		logger.info("Entering request for creating comment with post id:{} and user id:{}", postId, userId);
		CommentDto commentDto2 = this.commentService.createComment(commentDto, postId, userId);
		logger.info("Completed request for creating comment with post id:{}", postId);
		return new ResponseEntity<>(commentDto2, HttpStatus.CREATED);
	}

	/**
	 * @author Ekta
	 * @apiNote This method is for deleting comment
	 * @param commentId
	 * @return ApiResponse
	 */
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {
		logger.info("Entering request for deleting comment with comment id:{}", commentId);
		this.commentService.deleteComment(commentId);
		logger.info("Completing request for deleting comment with comment id:{}", commentId);
		return new ResponseEntity<>(new ApiResponse(AppConstants.COMMENT_DELETE, true), HttpStatus.OK);
	}
}
