package com.keanu.cloud.account.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keanu.cloud.account.client.AuthServiceClient;
import com.keanu.cloud.account.domain.Account;
import com.keanu.cloud.account.domain.User;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AuthServiceClient authServiceClient;
	
	@Override
	public Account create(User user) {
		
		authServiceClient.createUser(user);
		
		Account account = new Account();
		account.setName(user.getUsername());
		account.setLastSeen(new Date());
		
		return account;
	}

}
