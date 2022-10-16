package com.codewithriyaz.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithriyaz.blog.entities.Category;
import com.codewithriyaz.blog.entities.Post;
import com.codewithriyaz.blog.entities.User;
import com.codewithriyaz.blog.exceptions.ResourceNotFoundException;
import com.codewithriyaz.blog.payloads.PostDto;
import com.codewithriyaz.blog.payloads.PostResponse;
import com.codewithriyaz.blog.repositories.CategoryRepo;
import com.codewithriyaz.blog.repositories.PostRepo;
import com.codewithriyaz.blog.repositories.UserRepo;
import com.codewithriyaz.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto creatPost(PostDto postDto,Integer UserId, Integer CategoryId) {
		User user = this.userRepo.findById(UserId).orElseThrow(()-> new ResourceNotFoundException("User ", "User Id", UserId));
		Category category = this.categoryRepo.findById(CategoryId).orElseThrow(
				()-> new ResourceNotFoundException("Category ", "Category Id", CategoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName(postDto.getImageName());
		post.setAddDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post createdPost = this.postRepo.save(post);
		
		return this.modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatePostDto(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post", "postId", postId));
		post.setAddDate(new Date());
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		Post updatePost = this.postRepo.save(post);
		PostDto updatePostDto = this.modelMapper.map(updatePost, PostDto.class);
		return updatePostDto;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post ", "postId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto getpostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post ", "postId", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
				
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPost = pagePost.getContent();
		List<PostDto> postDtos = allPost.stream().map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Category ", "CategoryId", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User ", "userId", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
//		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

}
