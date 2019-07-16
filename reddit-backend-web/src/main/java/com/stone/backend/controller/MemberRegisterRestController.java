package com.stone.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stone.backend.domain.Member;
import com.stone.backend.dto.MemberDto;
import com.stone.backend.exception.MemberDuplicatedException;
import com.stone.backend.repository.MemberRepository;

@RestController()
public class MemberRegisterRestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MemberRepository mbrRepo;
	
	@Autowired
	PasswordEncoder defaultPasswordEncoder;
	
	@PostMapping(path = "/register")
    public MemberDto register(@RequestBody Member member) {
		
		if(member.getUsername()==null || member.getPassword()==null) {
			throw new IllegalArgumentException("the username or password can not be empty.");
		}
		
		if(mbrRepo.findByUsername(member.getUsername())!=null) {
			throw new MemberDuplicatedException();
		}
		
		try {
			member.setPassword(defaultPasswordEncoder.encode(member.getPassword()));
			member = mbrRepo.save(member);
			return new MemberDto(member.getId(), member.getUsername());
		} catch (Exception e) {
			logger.error("registration failed",e);
			throw new RuntimeException("create member failed.");
		}
    }
}
