package com.codewithdurgesh.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.CategoryDto;
import com.codewithdurgesh.blog.payloads.CategoryResponse;
import com.codewithdurgesh.blog.services.CategoryService;
import com.codewithdurgesh.blog.utils.AppConstants;

@RestController
@RequestMapping("/api")
public class CategoryController {

	Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService service;

	/**
	 * @author Ekta
	 * @apiNote This method is for creating category
	 * @param dto
	 * @return CategoryDto
	 */
	@PostMapping("/categories")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto) {
		logger.info("Entering request for create category");
		CategoryDto categoryDto = this.service.createCategory(dto);
		logger.info("Completed request for create category");
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.CREATED);

	}

	/**
	 * @author Ekta
	 * @apiNote This method is for updating category
	 * @param dto
	 * @param categoryId
	 * @return CategoryDto
	 */
	@PutMapping("/categories/{Id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto dto,
			@PathVariable("Id") Long categoryId) {
		logger.info("Entering request for update category with category id:{}", categoryId);
		CategoryDto categoryDto = this.service.updateCategory(dto, categoryId);
		logger.info("Completed request for update category with category id:{}", categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);

	}

	/**
	 * @author Ekta
	 * @apiNote This method is for deleting category
	 * @param categoryId
	 * @return ApiResponse
	 */
	@DeleteMapping("/categories/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
		logger.info("Entering request for delete category with category id:{}", categoryId);
		this.service.deleteCategory(categoryId);
		logger.info("Completed request for delete category with category id:{}", categoryId);
		return new ResponseEntity<>(new ApiResponse(AppConstants.CATEGORY_DELETE, true), HttpStatus.OK);

	}

	/**
	 * @author Ekta
	 * @apiNote This method is for getting single category
	 * @param categoryId
	 * @return CategoryDto
	 */
	@GetMapping("/categories/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId) {
		logger.info("Entering request to get single category with category id:{}",categoryId);
		CategoryDto categoryDto = this.service.getCategory(categoryId);
		logger.info("Completed request to get single category with category id:{}",categoryId);
		return new ResponseEntity<>(categoryDto, HttpStatus.OK);

	}

	/**
	 * @author Ekta
	 * @apiNote This method is for getting all categories in pages
	 * @param pageNo
	 * @param pageSize
	 * @return List<CategoryDto>
	 */
	@GetMapping("/categories")
	public ResponseEntity<CategoryResponse> getCategories(@RequestParam(value="pageNo", defaultValue= AppConstants.PAGE_No, required=false) Integer pageNo,
			@RequestParam(value="pageSize", defaultValue= AppConstants.PAGE_SIZE, required=false) Integer pageSize) {
        logger.info("Entering request to get all categories");
		CategoryResponse categoryResponse = this.service.getCategories(pageNo, pageSize);
		logger.info("Completed request to get all categories");
		return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.OK);

	}
	
	

}
