package com.codewithdurgesh.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.Comment;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.CommentDto;
import com.codewithdurgesh.blog.repositories.CommentRepository;
import com.codewithdurgesh.blog.repositories.PostRepository;
import com.codewithdurgesh.blog.repositories.UserRepository;
import com.codewithdurgesh.blog.services.CommentService;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Long postId, Long userId) {
		log.info("Initiating dao call to save Comment for post with postId {} and userId {}", postId, userId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "userId", userId));
		Comment comment = this.mapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		Comment savecomment = this.commentRepo.save(comment);
		CommentDto commentDto2 = this.mapper.map(savecomment, CommentDto.class);

		log.info("Completed dao call to save Comment for post with postId {} and userId {}", postId, userId);
		return commentDto2;
	}

	@Override
	public void deleteComment(Long commentId) {
		log.info("Initiating dao Call to Delete Comment  with commentID {}", commentId);
		Comment comment= this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
		
		this.commentRepo.delete(comment);
		log.info("Initiating dao Call to Delete Comment with CommentId {}", commentId);
		
	}

}
