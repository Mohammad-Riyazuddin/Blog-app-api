package com.codewithriyaz.blog.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.codewithriyaz.blog.exceptions.ApiException;
import com.codewithriyaz.blog.services.UserService;

@Aspect
@Component
public class AuthenticaionAspects {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Before("execution(* com.codewithriyaz.blog.controllers.PostController.createPosts(..))")
	public void beforePostCreate() throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String URL = request.getRequestURI();
		int afterUserId = URL.indexOf("/category/");
		String userId = URL.substring(10, afterUserId);
		String userEmailFromIdUrl = this.userService.getUserById(Integer.parseInt(userId)).getEmail();
		String token = request.getHeader("Authorization");
		String userEmailFromToken = this.jwtTokenHelper.getUsernameFromToken(token.substring(7));
		if(!userEmailFromIdUrl.equals(userEmailFromToken)) {
			throw new ApiException("Illegal Request");
		}
	}
}
