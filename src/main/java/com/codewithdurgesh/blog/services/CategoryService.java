package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.payloads.CategoryDto;
import com.codewithdurgesh.blog.payloads.CategoryResponse;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
	
	void deleteCategory(Long categoryId);
	
	CategoryDto getCategory(Long categoryId);
	
	CategoryResponse getCategories(Integer pageno, Integer pagesize);
}
