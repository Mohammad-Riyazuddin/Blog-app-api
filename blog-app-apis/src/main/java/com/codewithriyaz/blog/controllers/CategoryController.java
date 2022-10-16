package com.codewithriyaz.blog.controllers;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.codewithriyaz.blog.payloads.ApiResponse;
import com.codewithriyaz.blog.payloads.CategoryDto;
import com.codewithriyaz.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//create
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategoryDto, HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> udpateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("categoryId") Integer categoryId ){
		CategoryDto updateCategoryDto = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updateCategoryDto, HttpStatus.OK);
	}
	//delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId ) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully !!", true), 
				HttpStatus.OK);
	}
	
	
	//get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Integer categoryId ){
		CategoryDto CategoryDto = this.categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(CategoryDto, HttpStatus.OK);
	}
	
	//get All
	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryDto>> getCategory(){
		List<CategoryDto> categoryDtos = this.categoryService.getCategories();
		return new ResponseEntity<List<CategoryDto>>(categoryDtos, HttpStatus.OK);
	}
	
}
