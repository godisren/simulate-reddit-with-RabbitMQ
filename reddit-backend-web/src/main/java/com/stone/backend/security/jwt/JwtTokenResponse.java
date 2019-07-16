package com.stone.backend.security.jwt;

public class JwtTokenResponse {
	private String token;

	public JwtTokenResponse() {
		super();
	}

	public JwtTokenResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
