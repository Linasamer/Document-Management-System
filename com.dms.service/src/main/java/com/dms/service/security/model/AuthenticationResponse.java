package com.dms.service.security.model;

public class AuthenticationResponse {

	private final String jwt;
	private final String username;
	private final Long userId;

	public AuthenticationResponse(String jwt, Long userId, String username) {
		this.jwt = jwt;
		this.userId = userId;
		this.username = username;
	}

	public String getJwt() {
		return jwt;
	}
	
	public Long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}
}