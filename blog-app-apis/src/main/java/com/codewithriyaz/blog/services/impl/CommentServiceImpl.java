package com.codewithriyaz.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithriyaz.blog.entities.Comment;
import com.codewithriyaz.blog.entities.Post;
import com.codewithriyaz.blog.exceptions.ResourceNotFoundException;
import com.codewithriyaz.blog.payloads.CommentDto;
import com.codewithriyaz.blog.repositories.CommentRepo;
import com.codewithriyaz.blog.repositories.PostRepo;
import com.codewithriyaz.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		CommentDto savedCommentDto = this.modelMapper.map(savedComment, CommentDto.class); 
		return savedCommentDto;
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment Id", commentId));
		this.commentRepo.delete(comment);
	}

}
