package com.codewithriyaz.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	
	
	@Size(min = 4, message = "Category Title must be minimum of 4 characters !!")
	@NotBlank
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 4, max=100, message = "Category Description must be minimum of 4 characters !!")
	private String categoryDescription;
}
