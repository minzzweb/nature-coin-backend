package com.nature.config;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nature.common.security.CustomUserDetailsService;
import com.nature.common.security.jwt.filter.JwtAuthenticationFilter;
import com.nature.common.security.jwt.filter.JwtRequestFilter;
import com.nature.common.security.jwt.provider.JwtTokenProvider;
import com.nature.prop.NatureProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	
	// JWT 토큰을 제공하는 JwtTokenProvider 필드 선언
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationConfiguration authenticationConfiguration;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
		log.info("security config ...");
		
		http.cors();
		http .csrf(AbstractHttpConfigurer::disable)
			 .addFilterAt(new JwtAuthenticationFilter(authenticationManager(),  jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
	         .addFilterBefore(new JwtRequestFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) //사용자 정의 인증 필터추가
	         .sessionManagement(sessionManagement
	                 -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	         );
		
		return http.build();
	}	
	
	@Bean
    public AuthenticationManager authenticationManager() throws Exception 
    {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	//비밀번호 암호처리기 생성
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public UserDetailsService customUserDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		config.setExposedHeaders(Arrays.asList("Authorization","Content-Disposition"));
	
		source.registerCorsConfiguration("/**", config);

	return source;
	}
}
