package com.stone.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stone.backend.domain.Post;
import com.stone.backend.mq.PostClient;
import com.stone.backend.repository.PostRepository;

@RestController()
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private PostClient postClient;
	
	@PostMapping()
	public Post create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Post content) throws JsonProcessingException {
//		return postRepo.save(new Post(content.getContent(), userDetails.getUsername()));
		
		return postClient.createPost(content.getContent(), userDetails.getUsername());
	}
	
	@GetMapping()
	public List<Post> list(){
		return postRepo.findAllByOrderByCreatedDateAsc();
	}
}
