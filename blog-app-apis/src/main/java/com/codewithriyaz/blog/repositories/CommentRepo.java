package com.codewithriyaz.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithriyaz.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
