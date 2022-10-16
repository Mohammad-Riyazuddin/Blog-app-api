package com.codewithriyaz.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithriyaz.blog.config.AppConstants;
import com.codewithriyaz.blog.payloads.ApiResponse;
import com.codewithriyaz.blog.payloads.PostDto;
import com.codewithriyaz.blog.payloads.PostResponse;
import com.codewithriyaz.blog.services.FileService;
import com.codewithriyaz.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	// create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPosts(@RequestBody PostDto postDto,
			@PathVariable(name = "userId") Integer userId, @PathVariable(name = "categoryId") Integer categoryId) {
		PostDto createdPostDto = this.postService.creatPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPostDto, HttpStatus.CREATED);
	}

	// updatePost
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePosts(@RequestBody PostDto postDto,
			@PathVariable(name = "postId") Integer postId) {
		PostDto updatedPostDto = this.postService.updatePostDto(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
	}

	// getPostByUser
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable(name = "userId") Integer userId) {
		List<PostDto> postDtos = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}

	// getByCategory
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(name = "categoryId") Integer categoryId) {
		List<PostDto> postDtos = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}

	// getAllPosts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,	required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	// getPostById
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Integer postId) {
		PostDto postDtos = this.postService.getpostById(postId);
		return new ResponseEntity<PostDto>(postDtos, HttpStatus.OK);
	}
	
	// delete postById
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable(name = "postId") Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully !!",true), 
				HttpStatus.OK);
	}
	
	// search 
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable(name = "keywords") String keywords){
		List<PostDto> postDtos = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}
	
	//post image upload
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId) throws Exception{
		PostDto postDto = this.postService.getpostById(postId);
		
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatedPostDto = this.postService.updatePostDto(postDto, postId); 
		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
	}
	
	// method to serve files
	@GetMapping(value="/posts/image/{imageName}", produces=MediaType.IMAGE_JPEG_VALUE )
	public void downloadImage (@PathVariable("imageName")String imageName,
	        HttpServletResponse response
	) throws IOException {
	    InputStream resource = this.fileService.getResource(path, imageName) ;
	    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    StreamUtils.copy(resource, response.getOutputStream());
	}
}
