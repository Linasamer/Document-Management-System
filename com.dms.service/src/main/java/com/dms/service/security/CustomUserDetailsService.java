package com.dms.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("testuser".equals(username)) {
			return org.springframework.security.core.userdetails.User.withUsername("testuser").password("{noop}testpassword") // {noop} means no password encoding
					.roles("USER").build();
		} else {
			throw new UsernameNotFoundException("User not found");
		}
	}
}
