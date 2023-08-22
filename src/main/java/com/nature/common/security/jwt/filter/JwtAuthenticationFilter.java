package com.nature.common.security.jwt.filter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nature.common.security.domain.CustomUser;
import com.nature.common.security.jwt.constants.SecurityConstants;
import com.nature.common.security.jwt.provider.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//사용자 정의 인증필터 
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager,JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		
		setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
	}
	// JWT 생성
	@Override                //인증시도 
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		log.info("attemptAuthentication email  = " + email);
		log.info("attemptAuthentication password = " + password);
		Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password); //인증필터클래스
		
		return authenticationManager.authenticate(authenticationToken);
	}
	
    // JWT 생성 매서드 -> 토큰을 생성해서 응답헤더에 보냄
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {
		
		CustomUser user = ((CustomUser) authentication.getPrincipal());
	
		long userNo = user.getUserNo();
		String email = user.getEmail();
		
		log.info("CustomUser test : userNo " + userNo);
		log.info("CustomUser test : email " + email);

		List<String> roles = user.getAuthorities()
								.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList());

		String token = jwtTokenProvider.createToken(userNo, email, roles);

		response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
	}
	
	
	
}
