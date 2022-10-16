package com.codewithriyaz.blog.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Setter
@Getter
public class Role {
	
	@Id
	private int id;
	
	private String name;
}
