package com.codewithriyaz.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithriyaz.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
}
