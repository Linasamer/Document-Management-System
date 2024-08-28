package com.dms.service.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.service.entity.User;
import com.dms.service.repository.UserRepository;
import com.dms.service.security.model.UserDetailsImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
	    // Attempt to find the user by username
	    Optional<User> user = userRepository.findByUsername(username);

	    // If the user is not found, create a new user and save it to the database
	    User newUser = user.orElseGet(() -> {
			System.out.println("Add the user because it's not found in db but found in ldap");
	        User createdUser = new User();
	        createdUser.setUsername(username);
	        createdUser.setActive(1);
	        createdUser.setEmail(username+"@ejada.com");
	        createdUser.setPassword("defaultPassword"); // Set a default password, should be encrypted
	        createdUser.setRoles("ROLE_USER"); // Assign default roles
	        return userRepository.save(createdUser);
	    });

	    // Return UserDetails
	    return new UserDetailsImpl(newUser);
	}
	
	
//	public UserDetails loadUserByUsername(String username) {
//		Optional<User> user = userRepository.findByUsername(username);
//		
////		user.orElse(() -> new UsernameNotFoundException("User Not Found with username: " + username));
//
//		user.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
//
//		return user.map(UserDetailsImpl::new).get();
//	}
}
