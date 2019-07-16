package com.stone.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stone.backend.domain.Member;
import com.stone.backend.repository.MemberRepository;
import com.stone.backend.security.jwt.JwtUserDetails;

@Primary
@Service
public class MongoUserDetailsService implements UserDetailsService {

	@Autowired
	MemberRepository mbrRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Member member = mbrRepo.findByUsername(username);

		if (member == null)
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));

		return new JwtUserDetails(member.getUsername(), member.getPassword());

	}

}
