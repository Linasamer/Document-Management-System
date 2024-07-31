package com.dms.service.security;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dms.service.security.model.AuthenticationRequest;
import com.dms.service.security.model.AuthenticationResponse;
import com.dms.service.security.model.UserDetailsImpl;
import com.dms.service.services.ConfigurationService;

@Component
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final JwtUtilities jwtUtilities;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Value("${noLdapFlag}")
	private boolean noLdapFlag;

	@Autowired
	public AuthenticationService(AuthenticationManager authenticationManager, JwtUtilities jwtUtilities) {
		this.authenticationManager = authenticationManager;
		this.jwtUtilities = jwtUtilities;
	}

	public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
				authenticationRequest.getPassword());
		final UserDetailsImpl userDetails;

		if (noLdapFlag) {
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			// getContext(authenticationRequest.getUsername(), authenticationRequest.getPassword());
			userDetails = (UserDetailsImpl) customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		}

		String jwtToken = jwtUtilities.generateJwtToken(authenticationRequest.getUsername());
		return new AuthenticationResponse(jwtToken);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static DirContext getContext(String username, String password) throws Exception {
		Hashtable env = new Hashtable();

		String domain = ConfigurationService.getLDAPDomain();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");

		if (!username.contains("@")) {
			username = username + "@" + domain;
		}

		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);

		Exception exceptionIfAny = null;

		String[] LDAPIPS = { ConfigurationService.getLDAPIPS() };
		for (String element : LDAPIPS) {
			String url = ConfigurationService.getLDAPConnectionType() + "://" + element + ":" + ConfigurationService.getLDAPPort();
			env.put(Context.PROVIDER_URL, url);
			try {
				return new InitialLdapContext(env, null);
			} catch (Exception e) {
				exceptionIfAny = e;
				e.printStackTrace();
			}
		}

		throw exceptionIfAny;
	}
}
