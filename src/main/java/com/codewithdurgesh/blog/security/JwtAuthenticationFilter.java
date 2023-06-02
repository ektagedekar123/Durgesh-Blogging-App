package com.codewithdurgesh.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codewithdurgesh.blog.utils.SecurityConstants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 1. Get token from request
		String requestToken = request.getHeader(SecurityConstants.AUTHORIZATION);

		// Bearer 467823455gdgf

		System.out.println(requestToken);

		String username = null;
		String token = null;

		if (requestToken != null && requestToken.startsWith(SecurityConstants.BEARER)) {

			token = requestToken.substring(7);

			try {
				username = this.jwtTokenHelper.getUsernamefromToken(token);
			} catch (IllegalArgumentException e) {
				logger.warn("Unable to get Jwt Token");
			} catch (ExpiredJwtException e) {
				logger.warn("Jwt token has Expired");
			} catch (MalformedJwtException e) {
				logger.warn("Invalid Jwt");
			}
		} else {
			logger.info("Jwt token does not begin with Bearer");
		}

		// 2. Once we get the token, validate it
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailService.loadUserByUsername(username);

			if (this.jwtTokenHelper.validateToken(token, userDetails)) {

				// Sahi chal rha h
				// Authentication krna h

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				logger.info("Invalid Jwt token");
			}
		} else {
			logger.info("Username is null or Context is not null");
		}
		filterChain.doFilter(request, response);
	}

}