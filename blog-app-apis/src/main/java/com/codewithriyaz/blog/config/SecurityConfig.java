package com.codewithriyaz.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codewithriyaz.blog.security.CustomUserDetailService;
import com.codewithriyaz.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	public static final String[] PUBLIC_URLS = {
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**",
			"/api/categories/getAll",
			"/api/posts",
			"/api/category/{categoryId}/posts",
			"/api/posts/search/{keywords}",
			"/api/user/{userId}/posts"
	};
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.
		csrf().
		disable().
		authorizeHttpRequests().
		antMatchers(PUBLIC_URLS).permitAll().
		anyRequest().
		authenticated().
		and().
		exceptionHandling().
		authenticationEntryPoint(this.authenticationEntryPoint).
		and().
		sessionManagement().
		sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
		return defaultSecurityFilterChain;
	}
	
	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		csrf().
		disable().
		authorizeHttpRequests().
		antMatchers(PUBLIC_URLS).permitAll().
		anyRequest().
		authenticated().
		and().
		exceptionHandling().
		authenticationEntryPoint(this.authenticationEntryPoint).
		and().
		sessionManagement().
		sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	*/
	
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}
	*/
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	*/
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
}
