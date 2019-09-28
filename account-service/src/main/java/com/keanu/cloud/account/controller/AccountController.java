package com.keanu.cloud.account.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.keanu.cloud.account.domain.Account;
import com.keanu.cloud.account.domain.User;
import com.keanu.cloud.account.service.AccountService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping("/")
	public Account createNewAccount(@Valid @RequestBody User user) {
		return accountService.create(user);
	}
	
}
