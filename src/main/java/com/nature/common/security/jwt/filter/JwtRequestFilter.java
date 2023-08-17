package com.nature.common.security.jwt.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nature.common.security.jwt.constants.SecurityConstants;
import com.nature.common.security.jwt.provider.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader(SecurityConstants.TOKEN_HEADER);
		
		if (isEmpty(header) || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
			}
		Authentication authentication = jwtTokenProvider.getAuthentication(header);
	
	
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
		
	}
	
}
