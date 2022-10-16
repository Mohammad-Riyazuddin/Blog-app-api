package com.codewithriyaz.blog.services;

import com.codewithriyaz.blog.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);
}
