package com.codewithriyaz.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotBlank
	@Size(min = 4, message = "Username must be minimum of 4 characters !!")
	private String name;
	
	@Email(message = "Email address is not valid !!")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String email;
	
	@NotBlank
	@Size(min = 3, max = 10, message = "Password be min of 3 chars and max of 10 chars")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@NotBlank
	@JsonProperty(access = Access.WRITE_ONLY)
	private String about;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<RoleDto> roles = new HashSet<>();
}
