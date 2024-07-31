package com.dms.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dms.service.security.model.AuthenticationRequest;
import com.dms.service.security.model.AuthenticationResponse;

@Component
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final JwtUtilities jwtUtilities;

	@Autowired
	public AuthenticationService(AuthenticationManager authenticationManager, JwtUtilities jwtUtilities) {
		this.authenticationManager = authenticationManager;
		this.jwtUtilities = jwtUtilities;
	}

	public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
				authenticationRequest.getPassword());

		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwtToken = jwtUtilities.generateJwtToken(authenticationRequest.getUsername());
		return new AuthenticationResponse(jwtToken);
	}
}
