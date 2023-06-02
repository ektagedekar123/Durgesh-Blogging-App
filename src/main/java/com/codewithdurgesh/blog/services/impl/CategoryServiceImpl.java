package com.codewithdurgesh.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.CategoryDto;
import com.codewithdurgesh.blog.payloads.CategoryResponse;
import com.codewithdurgesh.blog.repositories.CategoryRepository;
import com.codewithdurgesh.blog.services.CategoryService;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category catogery = mapper.map(categoryDto, Category.class);
		log.info("Initiating dao layer for Creating Category");
		Category catogery2 = categoryRepo.save(catogery);
		log.info("Initiating dao layer for Creating Category");
		return this.mapper.map(catogery2, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		log.info("Initiating dao call to Update category with categoryId:{}", categoryId);
		Category catogery = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		catogery.setCategoryDescription(categoryDto.getCategoryDescription());
		catogery.setCategoryTitle(categoryDto.getCategoryTitle());
		Category category = categoryRepo.save(catogery);
		log.info("Completed dao call to update category with categoryId):{}", categoryId);
		return this.mapper.map(category , CategoryDto.class);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		log.info("Initiating dao call to Delete category with categoryId :{}", categoryId);
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new
		 ResourceNotFoundException("Category", "CategoryId",categoryId));
		categoryRepo.delete(category);
		log.info("Completed dao call to delete category with categoryId :{}", categoryId);
	}

	@Override
	public CategoryDto getCategory(Long categoryId) {
		log.info("Initiating dao call to get category with categoryId :{}", categoryId);
		Category catogery = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CaategoryId", categoryId));
		log.info("Completed dao call to get category with categoryId :{}", categoryId);

		return this.mapper.map(catogery, CategoryDto.class);
	}

	@Override
	public CategoryResponse getCategories(Integer pageno, Integer pagesize) {
		log.info("Initiating dao call to get all categories in pages");
		Pageable of = PageRequest.of(pageno, pagesize);
		Page<Category> page = categoryRepo.findAll(of);
		List<Category> categories = page.getContent();
		
		List<CategoryDto> collect = categories.stream().map(c-> mapper.map(c, CategoryDto.class)).collect(Collectors.toList());
		
		CategoryResponse categoryResponse=new CategoryResponse();
		categoryResponse.setContent(collect);
		categoryResponse.setPageNo(page.getNumber());
		categoryResponse.setPageSize(page.getSize());
		categoryResponse.setTotalElements(page.getTotalElements());
		categoryResponse.setTotalPages(page.getTotalPages());
		categoryResponse.setLastPage(page.isLast());
		log.info("Completed dao call to get all categories in pages");
		return categoryResponse;
	}

}
