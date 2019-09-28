package com.keanu.cloud.account.service;

import com.keanu.cloud.account.domain.Account;
import com.keanu.cloud.account.domain.User;

public interface AccountService {

	Account create(User user);
}
