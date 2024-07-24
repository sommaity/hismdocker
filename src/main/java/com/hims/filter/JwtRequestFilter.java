package com.hims.filter;

import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.hims.jwt.JwtConfig;
import com.hims.model.User;
import com.hims.service.MyUserService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private MyUserService userService;
	@Autowired
	private JwtConfig jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException{
		System.out.println(request);
		final String jwtRequestHeader = request.getHeader("Authorization");
		String jwt = null, username = null;
		if (jwtRequestHeader != null && jwtRequestHeader.startsWith("Bearer ")) {
			jwt = jwtRequestHeader.substring(7);
			try {
				username = jwtService.extractUserName(jwt);
			} catch (Exception e) {
				//System.out.println(jwt+"  catch "+e.getMessage());
			}
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			User userDetails=userService.loadUserByUsername(username);
			if (jwtService.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
