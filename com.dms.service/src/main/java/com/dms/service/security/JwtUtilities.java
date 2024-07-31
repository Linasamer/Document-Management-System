package com.dms.service.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtilities {

	@Value("${jwt.secretKey}")
	private String secretKey;

	@Value("${jwt.expirationMs}")
	private int expirationMs;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	public String generateJwtToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expirationMs))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}

	public String getUsernameFromJwtToken(String jwtToken) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody().getSubject();
	}

	public Authentication getAuthentication(String jwtToken) {
		String username = getUsernameFromJwtToken(jwtToken);
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
