package com.codewithriyaz.blog.services;

import java.util.List;

import com.codewithriyaz.blog.payloads.PostDto;
import com.codewithriyaz.blog.payloads.PostResponse;

public interface PostService {
	
	//create
	PostDto creatPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update
	PostDto updatePostDto(PostDto postDto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get
	PostDto getpostById(Integer postId);
	
	//get all
	PostResponse getAllPost(Integer pageSize, Integer pageNumber, String sortBy, String sortDir);
	
	//get all post by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//get all post by User
	List<PostDto> getPostsByUser(Integer userId);
	
	//search post by keyword
	List<PostDto> searchPosts(String keyword);
}
