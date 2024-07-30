package com.code.secretary.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dms.service.security.RequestFilter;

@EnableWebSecurity
public class AppSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	// private UserDetailsServiceImpl userDetailsServiceImpl;

	private RequestFilter requestFilter;

	@Autowired
	public AppSecurityConfigurerAdapter(RequestFilter requestFilter) {
		this.requestFilter = requestFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/api/authentication").permitAll().antMatchers("/api/util/attachments/saveUploadedAttachment")
				.permitAll()

				.antMatchers("/api/websocket/**").permitAll()

				.antMatchers("/api/prayTime/**").permitAll() // called by the scheduler
				.antMatchers("/api/request/**/scheduler").permitAll() // called by the scheduler

				.antMatchers("/swagger-ui**/**").permitAll().antMatchers("/v3/api-docs/**").permitAll()

				.anyRequest().authenticated().and().cors().and().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.headers().frameOptions().sameOrigin();

		// TODO For Dev Only (Don't commit it)
		/*
		 * http.authorizeRequests().antMatchers("/api/**").permitAll() .anyRequest().permitAll() .and().cors() .and().csrf().disable() .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 */

		http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	// @Override
	// public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
	// authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	// }

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTION", "DELETE", "PUT"));
		configuration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
