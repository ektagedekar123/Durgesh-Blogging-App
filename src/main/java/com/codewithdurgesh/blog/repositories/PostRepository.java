package com.codewithdurgesh.blog.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.entities.User;

public interface PostRepository extends JpaRepository<Post, Long>{

	Slice<Post> findByUser(User user, Pageable pageable);
	
	Slice<Post> findByCategory(Category category, Pageable pageable);
	
	List<Post> findByTitleContaining(String title);  // Internally like query is fired
	                                                 // "select p from Post p where p.title like :key"
}
