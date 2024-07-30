package com.dms.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dms.service.exceptions.BusinessException;
import com.dms.service.security.model.AuthenticationRequest;
import com.dms.service.security.model.AuthenticationResponse;
import com.dms.service.security.model.UserDetailsImpl;

@Service
public class AuthenticationService {
	@Value("${ldap.enabled}") // get flag from application.properties
	private boolean ldapEnabled;

	private SecurityService securityService;

	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthenticationService(SecurityService securityService, AuthenticationManager authenticationManager) {
		this.securityService = securityService;
		this.authenticationManager = authenticationManager;
	}

	// TODO: exception handling
	public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) {
		if (!securityService.authenticateUser(authenticationRequest.getUsername(), authenticationRequest.getPassword(), ldapEnabled))
			throw new BusinessException("error_invalidUserNameOrPassword");

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
				authenticationRequest.getSystemPassword());
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = JwtUtilities.generateJwtToken(authenticationRequest.getUsername());
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return new AuthenticationResponse(token);
	}

}
