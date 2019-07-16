package com.stone.redditbackend;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCEncrypt {

	@Test
	public void test() {
		BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        System.out.println(encoder.encode("stone"));
	}
}
