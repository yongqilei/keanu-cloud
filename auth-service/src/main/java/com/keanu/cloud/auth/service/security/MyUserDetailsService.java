package com.keanu.cloud.auth.service.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.keanu.cloud.auth.domain.User;
import com.keanu.cloud.auth.mapper.UserMapper;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> existing = Optional.ofNullable(userMapper.selectByUsername(username));
		
		return existing.orElseThrow(() -> { throw new UsernameNotFoundException(username); });
	}

}
