package com.stone.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stone.backend.security.jwt.JwtTokenRequest;
import com.stone.backend.security.jwt.JwtTokenResponse;
import com.stone.backend.security.jwt.JwtTokenUtil;

@RestController
public class JwtAuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping(path = "/authentication")
    public JwtTokenResponse authenticate(@RequestBody JwtTokenRequest request) {
        authenticate(request.getUsername(), request.getPassword());
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);
        
        return new JwtTokenResponse(token);
    }

	private void authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
}
