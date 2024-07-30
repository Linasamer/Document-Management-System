package com.dms.service.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtilities {

	private static String secretKey;

	@Value("${jwt.secretKey}")
	public void setSecretKey(String name) {
		JwtUtilities.secretKey = name;
	}

	private static int expirationMs;

	@Value("${jwt.expirationMs}")
	public void setExpirationMs(int expirationMs) {
		JwtUtilities.expirationMs = expirationMs;
	}

	public static String generateJwtToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expirationMs))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}

	public static String getUsernameFromJwtToken(String jwtToken) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody().getSubject();
	}
}
