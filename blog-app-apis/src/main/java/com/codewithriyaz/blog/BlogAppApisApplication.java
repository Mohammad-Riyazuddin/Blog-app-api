package com.codewithriyaz.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codewithriyaz.blog.config.AppConstants;
import com.codewithriyaz.blog.entities.Role;
import com.codewithriyaz.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
		
		
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("abcd"));
		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("NORMAL_USER");
			List<Role> roles = List.of(role,role2);
			this.roleRepo.saveAll(roles);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
