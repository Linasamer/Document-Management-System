package com.dms.service.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

	@Value("${setup.down}") // TODO: remove
	private boolean setupDown;

	public boolean authenticateUser(String username, String password, boolean ldapFlag) {
		if (setupDown) {
			return true;
		} else {
			// return RestClientService.getObject(ExternalServerEnum.SECURITY, ExternalWebservicesEnum.SECURITY_AUTHENTICATE_USER.getPath(),
			// Boolean.class, username, password, ldapFlag);
			return false;
		}
	}

}
