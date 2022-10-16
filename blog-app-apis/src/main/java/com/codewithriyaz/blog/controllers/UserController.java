package com.codewithriyaz.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithriyaz.blog.payloads.ApiResponse;
import com.codewithriyaz.blog.payloads.UserDto;
import com.codewithriyaz.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	//POST-create user
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createUserDto, HttpStatus.CREATED);
	}
	//PUT-update user
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> udpateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId){
		UserDto updateUserDto = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updateUserDto, HttpStatus.OK);
	}
	
	//DELETE-delete user
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
	}
	//GET - user get
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return new ResponseEntity<List<UserDto>>(this.userService.getAllUsers(), HttpStatus.OK);
	}
	//GET - get single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return new ResponseEntity<UserDto>(this.userService.getUserById(userId), HttpStatus.OK);
	}
	
	
}
