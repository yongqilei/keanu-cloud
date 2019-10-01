package com.keanu.cloud.account.controller;

import javax.validation.Valid;

import com.keanu.cloud.account.client.AuthServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.keanu.cloud.account.domain.Account;
import com.keanu.cloud.account.domain.User;
import com.keanu.cloud.account.service.AccountService;

import java.security.Principal;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AuthServiceClient authServiceClient;
	
	@PostMapping("/")
	public Account createNewAccount(@Valid @RequestBody User user) {
		return accountService.create(user);
	}

	@GetMapping("/current")
	public Principal getUser(Principal principal) {
		return authServiceClient.getUser(principal);
	}
	
}
