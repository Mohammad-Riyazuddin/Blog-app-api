package com.codewithriyaz.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithriyaz.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
