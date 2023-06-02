package com.codewithdurgesh.blog.controllers;

import java.io.IOException;


import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;
import com.codewithdurgesh.blog.payloads.PostResponse1;
import com.codewithdurgesh.blog.services.FileService;
import com.codewithdurgesh.blog.services.PostService;
import com.codewithdurgesh.blog.utils.AppConstants;

@RestController
@RequestMapping("/api")
public class PostController {
	
	Logger logger = LoggerFactory.getLogger(PostController.class); 

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

    
	/**
	 * @author Ekta
	 * @apiNote This method is for creating Post
	 * @param postdto
	 * @param userId
	 * @param categoryId
	 * @return PostDto
	 */
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postdto, @PathVariable Long userId,
			@PathVariable Long categoryId) {
        logger.info("Entering request for create Post with user id:{} and category id:{}",userId,categoryId);
		PostDto postDto2 = this.postService.createPost(postdto, userId, categoryId);
		logger.info("Completed request for create Post");
		return new ResponseEntity<PostDto>(postDto2, HttpStatus.CREATED);

	}
	
   
	/**
	 * @author Ekta
	 * @apiNote This method is for updating post
	 * @param postDto
	 * @param postId
	 * @return PostDto
	 */
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Long postId){
		logger.info("Entering request for updating Post with post id:{}", postId);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		logger.info("Completed request for updating Post with post id:{}", postId);
		return new ResponseEntity<>(updatePost, HttpStatus.OK);
		
	}
	
    /**
     * @author Ekta
     * @apiNote This method is for deleting post
     * @param postId
     * @return ApiResponse
     */
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId){
		logger.info("Entering request for delete Post with postId:{}",postId);
		this.postService.deletePost(postId);
		logger.info("Completed request for delete post with postId:{)",postId);
		return new ResponseEntity<>(new ApiResponse(AppConstants.POST_DELETE, true), HttpStatus.OK);
	}
	
    /**
     * @author Ekta
     * @apiNote This method is for getting single post
     * @param postId
     * @return PostDto
     */	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable Long postId){
		logger.info("Entering request to get single Post with post id:{}",postId);
		PostDto postDto= this.postService.getPostById(postId);
		logger.info("Completed request to get single Post with post id:{}",postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
    /**
     * @author Ekta
     * @apiNote This method is for getting all posts by implementing pagination & sorting
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return PostResponse
     */	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNo", defaultValue= AppConstants.PAGE_No, required=false) Integer pageNo,
			@RequestParam(value="pageSize", defaultValue= AppConstants.PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstants.SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue=AppConstants.SORT_DIR, required=false) String sortDir){
		logger.info("Entering request to get all posts");
		PostResponse postResponse = this.postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
		logger.info("Completed request to get all posts");
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	

	/**
	 * @author Ekta
	 * @apiNote This method is for getting Post by User id in pages & sort records
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return List<PostDto>
	 */	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse1> getPostByUser(@PathVariable Long userId,
			@RequestParam(value="pageNo", defaultValue= AppConstants.PAGE_No, required=false) Integer pageNo,
			@RequestParam(value="pageSize", defaultValue= AppConstants.PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstants.SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue=AppConstants.SORT_DIR, required=false) String sortDir){
		logger.info("Entering request to get post by User Id:{}",userId);
		PostResponse1 postResponse1 = this.postService.getPostByUser(userId, pageNo, pageSize, sortBy, sortDir);
		logger.info("Completed request to get post by User Id:{}",userId);
		return new ResponseEntity<>(postResponse1, HttpStatus.OK);
		
	}
	
	
	/**
	 * @author Ekta
	 * @apiNote This method is for getting post by Category id in pages & sort records
	 * @param categoryId
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return List<PostDto>
	 */	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse1> getPostByCategory(@PathVariable Long categoryId,
			@RequestParam(value="pageNo", defaultValue= AppConstants.PAGE_No, required=false) Integer pageNo,
			@RequestParam(value="pageSize", defaultValue= AppConstants.PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstants.SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue=AppConstants.SORT_DIR, required=false) String sortDir){
		logger.info("Entering request to get post by Category Id:{}",categoryId);
		PostResponse1 postResponse1 = this.postService.getPostByCategory(categoryId,  pageNo, pageSize, sortBy, sortDir);
		logger.info("Completed request to get post by Category Id:{}",categoryId);
		return new ResponseEntity<>(postResponse1, HttpStatus.OK);
		
	}
	
    
	/**
	 * @author Ekta
	 * @apiNote This method is for searching post by title
	 * @param keywords
	 * @return List<PostDto>
	 */	
	@GetMapping(("/posts/search/{keywords}"))
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
		logger.info("Entering request to Search post by title with keywords:{}",keywords);
		List<PostDto> postDtos = this.postService.searchPosts(keywords);
		logger.info("Completed request to Search post by title with keywords:{}",keywords);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}

	
	/**
	 * @author Ekta
	 * @apiNote This method is for uploading image
	 * @param image
	 * @param pid
	 * @return PostDto
	 * @throws IOException
	 */	
	@PostMapping("/posts/Image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestPart("image") MultipartFile image,
			                                        @PathVariable("postId") Long pid) throws IOException{
		logger.info("Entering request to upload image with post Id:{}",pid);
		PostDto postDto = this.postService.getPostById(pid);
		String filename = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(filename);
		PostDto updatePost = this.postService.updatePost(postDto, pid);
		logger.info("Completed request to upload image with post Id:{}",pid);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	
    /**
     * @author Ekta
     * @apiNote This method is for downloading image
     * @param imageName
     * @param response
     * @throws IOException
     */	
	@GetMapping(value="/post/Image/download/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void dowloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
		logger.info("Entering request to download image with image name:{}",imageName);
		InputStream resource = this.fileService.getresource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		logger.info("Completed request to download image with image name:{}",imageName);
	}
	
	

}
