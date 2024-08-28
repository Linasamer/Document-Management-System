package com.dms.service.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



@Component
public class RequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtilities jwtUtilities;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try { 
			String jwtToken = parseJwt(request);

		if (jwtToken != null && jwtUtilities.getUsernameFromJwtToken(jwtToken) != null) {
			Authentication authentication = jwtUtilities.getAuthentication(jwtToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session Expired");

//		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//		    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//		    String timestamp = dateFormat.format(new Date());
//
//		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//		    response.setContentType("application/json");
//		    response.getWriter()
//		    .write("{ \"timestamp\": \"" + timestamp + "\", \"status\": 401, \"error\": \"Unauthorized\", \"message\": \"" + "Session Expired" + "\", \"path\": \"" + request.getRequestURI() + "\" }");
		}
	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}
		return null;
	}
}
