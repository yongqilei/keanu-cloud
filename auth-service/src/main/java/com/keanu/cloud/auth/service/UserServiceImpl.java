package com.keanu.cloud.auth.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.keanu.cloud.auth.domain.User;
import com.keanu.cloud.auth.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void createUser(User user) {
		Optional<User> existing = Optional.ofNullable(userMapper.selectByUsername(user.getUsername()));
		
		existing.ifPresent(it -> { throw new IllegalArgumentException("user already exists: " + it.getUsername()); });
		
		String hash = passwordEncoder.encode(user.getPassword());
		user.setPassword(hash);
		
		userMapper.insert(user);
		
		log.info("new user has been created: {}", user.getUsername());

	}

}
