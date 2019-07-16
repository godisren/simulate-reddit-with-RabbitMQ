package com.stone.redditbackend.repository;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.stone.backend.RedditBackendApplication;
import com.stone.backend.domain.Member;
import com.stone.backend.repository.MemberRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedditBackendApplication.class)
@TestPropertySource(locations = "/application-test.properties")
public class MemberRepositoryTest {
	
	@Autowired
	private MemberRepository repo;
	
	@Test
	public void testInsertAndFetch() {
		
		repo.deleteAll();
		
		Member member = new Member();
		member.setUsername("stone");
		member.setPassword("test");
		member.setLastname("huang");
		member.setFirstname("stone");
//		member.setRegisteredDate(new Date());
		Member savedMember = repo.save(member);
		Assert.assertNotNull(savedMember.getId());
		Assert.assertNotNull(savedMember.getRegisteredDate());
		
		repo.findAll().forEach(System.out::println);
		
		System.out.println("findByUsername(\"stone\")");
		System.out.println(repo.findByUsername("stone"));
		System.out.println("countByUsername(\"stone\")");
		System.out.println(repo.countByUsername("stone"));
		
	}
	
	@Test
	public void testFieldValidate() {
		Member member = new Member();
		
		try {
			repo.save(member);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Assert.assertEquals(ConstraintViolationException.class, e.getClass());
		}
		
		try {
			member.setPassword("test");
			repo.save(member);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Assert.assertEquals(ConstraintViolationException.class, e.getClass());
		}
		
		try {
			member.setPassword(null);
			member.setUsername("test");
			repo.save(member);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Assert.assertEquals(ConstraintViolationException.class, e.getClass());
		}
	}
}
