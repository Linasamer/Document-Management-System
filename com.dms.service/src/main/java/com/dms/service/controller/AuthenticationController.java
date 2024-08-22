package com.dms.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dms.service.exceptions.BusinessException;
import com.dms.service.security.AuthenticationService;
import com.dms.service.security.model.AuthenticationRequest;
import com.dms.service.security.model.AuthenticationResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@Autowired
	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/authentication")
	public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			return this.authenticationService.authenticateUser(authenticationRequest);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("error_invalidUserNameOrPassword", e);
		}
	}
}
