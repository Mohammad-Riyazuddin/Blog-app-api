package com.codewithriyaz.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithriyaz.blog.exceptions.ApiException;
import com.codewithriyaz.blog.payloads.JwtAuthRequest;
import com.codewithriyaz.blog.payloads.JwtAuthResponse;
import com.codewithriyaz.blog.payloads.UserDto;
import com.codewithriyaz.blog.security.JwtTokenHelper;
import com.codewithriyaz.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {
		this.authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
	}
	
	private void authenticate(String username, String password) throws Exception{
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid details");
			throw new ApiException("Invalid username and password");
		}	
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUSer(@RequestBody UserDto userDto){
		UserDto registerUserDto = this.userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(registerUserDto, HttpStatus.CREATED);
	}
	
}
