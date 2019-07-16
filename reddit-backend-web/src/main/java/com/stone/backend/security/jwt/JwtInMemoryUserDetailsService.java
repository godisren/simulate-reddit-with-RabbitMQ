package com.stone.backend.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// hard code user
		if("stone".contentEquals(username)) {
			return new JwtUserDetails("stone","$2a$10$ekQRuAOwcawNB30GADVCAORwLMUHayEvUK4yg3dJjx6NNai0tDqPW");	// pwd : Encrypt_with_BCrypt('stone')
		}
		
		throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
	}

}
