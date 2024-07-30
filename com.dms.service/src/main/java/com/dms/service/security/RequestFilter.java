package com.dms.service.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class RequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO: exception handling
		try {
			String jwtToken = parseJwt(request);
			String username = null;

			if (jwtToken != null)
				username = JwtUtilities.getUsernameFromJwtToken(jwtToken);

			if (username != null) {
				// UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

				// UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
				// userDetails.getAuthorities());
				// authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}

			response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private String parseJwt(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7, authorizationHeader.length());
		}

		return null;
	}
}