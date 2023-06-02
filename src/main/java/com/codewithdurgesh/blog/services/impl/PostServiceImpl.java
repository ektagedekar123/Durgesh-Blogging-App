package com.codewithdurgesh.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;
import com.codewithdurgesh.blog.payloads.PostResponse1;
import com.codewithdurgesh.blog.repositories.CategoryRepository;
import com.codewithdurgesh.blog.repositories.PostRepository;
import com.codewithdurgesh.blog.repositories.UserRepository;
import com.codewithdurgesh.blog.services.PostService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private UserRepository userrepo;

	@Override
	public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {
		log.info("Initiating dao layer to create Post with userId: {} and categoryId: {}", userId, categoryId);
		User user = userrepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Userid", userId));

		Category catogery = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryid", categoryId));

		Post post = this.mapper.map(postDto, Post.class);
//		post.setImageName("default.png");
		post.setImageName(postDto.getImageName());
		post.setAddedDate(new Date());
		post.setCategory(catogery);
		post.setUser(user);

		Post post2 = postRepo.save(post);

		log.info("Completed dao layer to create Post with userId: {} and categoryId: {}", userId, categoryId);
		return this.mapper.map(post2, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long postid) {
		log.info("Initiating dao layer to Update Post with postId:{}", postid);
		Post post = this.postRepo.findById(postid)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postid));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post post2 = this.postRepo.save(post);

		PostDto postDto2 = this.mapper.map(post2, PostDto.class);
		log.info("Completed dao layer to Update Post with postId:{}", postid);
		return postDto2;
	}

	@Override
	public void deletePost(Long postid) {
		log.info("Initiating dao layer to Delete Post with postId:{}", postid);
		Post post = postRepo.findById(postid)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postid));
		this.postRepo.delete(post);
		log.info("Completed dao layer to Delete Post with postId:{}", postid);
	}

	@Override
	public PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
		log.info("Initiating dao layer to Get all Post with pagination");
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable of = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> page = postRepo.findAll(of); // return page of post
		List<Post> list = page.getContent();

		List<PostDto> list2 = list.stream().map((p) -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
		log.info("Successfully Fetch All Records from database");
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(list2);
		postResponse.setPageNo(page.getNumber()); // return no of current Slices
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElements(page.getTotalElements()); // total elements from page
		postResponse.setTotalPages(page.getTotalPages()); // total pages to store all records
		postResponse.setLastPage(page.isLast()); // is current page is last or not

		log.info("Completed dao layer to Get all Post with pagination");
		return postResponse;
	}

	@Override
	public PostDto getPostById(Long postid) {
		log.info("Initiating dao layer to Get Post with postId:{}", postid);
		Post post = postRepo.findById(postid)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postid));
		log.info("Initiating dao layer to Get Post with postId:{}", postid);
		return this.mapper.map(post, PostDto.class);

	}

	@Override
	public PostResponse1 getPostByUser(Long userId, Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
		log.info("Initiating dao layer to Get Post with userId:{}", userId);
		User user = this.userrepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Userid", userId));
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Slice<Post> sliceResult = this.postRepo.findByUser(user, pageable);
		List<Post> posts = sliceResult.getContent();

		List<PostDto> collect = posts.stream().map((u) -> this.mapper.map(u, PostDto.class))
				.collect(Collectors.toList());

		PostResponse1 postResponse1 = new PostResponse1();
		postResponse1.setContent(collect);
		postResponse1.setPageNo(sliceResult.getNumber());
		postResponse1.setPageSize(sliceResult.getSize());
		postResponse1.setTotalElements(sliceResult.getNumberOfElements());

		log.info("Completed dao layer to get post with userId:{}", userId);
		return postResponse1;
	}

	@Override
	public PostResponse1 getPostByCategory(Long categoryId, Integer pageNo, Integer pageSize, String sortBy,
			String sortDir) {
		log.info("Initiating dao layer to Get Post with CategoryId:{}", categoryId);
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Categoryid", categoryId));

		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Slice<Post> sliceResult = this.postRepo.findByCategory(category, pageable);
		List<Post> posts = sliceResult.getContent();
		List<PostDto> collect = posts.stream().map((p) -> mapper.map(p, PostDto.class)).collect(Collectors.toList());

		PostResponse1 postResponse1 = new PostResponse1();
		postResponse1.setContent(collect);
		postResponse1.setPageNo(sliceResult.getNumber());
		postResponse1.setPageSize(sliceResult.getSize());
		postResponse1.setTotalElements(sliceResult.getNumberOfElements());
		log.info("Completed dao layer to Get Post with CategoryId:{}", categoryId);
		return postResponse1;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> listOfpost = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> listofPoseDto = listOfpost.stream().map((post) -> this.mapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return listofPoseDto;
	}

}
