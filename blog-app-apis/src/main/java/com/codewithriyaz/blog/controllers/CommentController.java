package com.codewithriyaz.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithriyaz.blog.payloads.ApiResponse;
import com.codewithriyaz.blog.payloads.CommentDto;
import com.codewithriyaz.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	//create comment
	@PostMapping("posts/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable("postId") Integer postId){
		CommentDto createComment = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}
	
	//delete comment
	@DeleteMapping("comment/{commentId}")
	public ResponseEntity<ApiResponse> createComment(@PathVariable("commentId") Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully!!", true), HttpStatus.OK);
	}
}
