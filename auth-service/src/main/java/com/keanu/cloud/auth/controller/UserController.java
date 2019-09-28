package com.keanu.cloud.auth.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keanu.cloud.auth.domain.User;
import com.keanu.cloud.auth.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/current")
	public Principal getUser(Principal principal) {
		return principal;
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@PostMapping
	public void createUser(User user) {
		userService.createUser(user);
	}
}
