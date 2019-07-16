package com.stone.redditbackend;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.stone.backend.RedditBackendApplication;
import com.stone.backend.security.jwt.JwtTokenUtil;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedditBackendApplication.class)
@WebAppConfiguration
public class JwtTokenUtilTest {
	
	@Autowired
	private JwtTokenUtil util;
	
	@Test
	public void testGenerateToken() {
		UserDetails user =  mock(UserDetails.class);
		when(user.getUsername()).thenReturn("stone");
		
		System.err.println(user.getUsername());
		
		String token = util.generateToken(user);
		System.out.println("generated token:"+token);
		
		System.out.println("created time:"+util.getIssuedAtDateFromToken(token));
		System.out.println("expiration time:"+util.getExpirationDateFromToken(token));
		
		Assert.assertTrue(util.validateToken(token, user));		
	}
	
	@Test
	public void test() {
		Assert.assertNotNull(util);
		
		/**
		 * debug online : https://jwt.io/
		 * 
		 * header:
		 	{
		 		"alg": "HS256",
  				"typ": "JWT"
		 	}
		   playload:
		 	{
			  "sub": "1234567890",
			  "name": "John Doe",
			  "iat": 1516239022,		-> issue at date
			  "exp": 1516250022			-> expiration date
			}
		 * 
		 */
		
		
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE1MTYyNTAwMjJ9.qxTWm2uaXmIiZR_VerNi3BKrIr0TRJ_YY5mK989WMxc";
		
		String username = util.getUsernameFromToken(token);
		System.out.println("username:"+username);
		Assert.assertEquals("1234567890", username);
		
		Date issuedDate = util.getIssuedAtDateFromToken(token);
		System.out.println("issuedDate:"+issuedDate);
		
		Date expirationDate = util.getExpirationDateFromToken(token);
		System.out.println("expirationDate:"+expirationDate);
		
		System.out.println("isTokenExpired:"+util.isTokenExpired(token));
		
	}
}
