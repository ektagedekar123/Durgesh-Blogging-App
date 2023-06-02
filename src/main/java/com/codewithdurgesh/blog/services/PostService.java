package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;
import com.codewithdurgesh.blog.payloads.PostResponse1;

public interface PostService {

	PostDto createPost(PostDto postDto, Long userId, Long categoryId);
	
	PostDto updatePost(PostDto postDto, Long postid);
	
	void deletePost(Long postid);
	
	// get All Posts
	PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir);
	
	// get Single Post	
	PostDto getPostById(Long postid);
	
	// get Posts By User
	PostResponse1 getPostByUser(Long userId,Integer pageNo, Integer pageSize, String sortBy, String sortDir);
	
	// get Posts By Category
	PostResponse1 getPostByCategory(Long categoryId,Integer pageNo, Integer pageSize, String sortBy, String sortDir);
	
	// Search posts
	List<PostDto> searchPosts(String keyword);
}
