package com.nature.config;
import java.util.Arrays;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nature.common.security.jwt.filter.JwtAuthenticationFilter;
import com.nature.common.security.jwt.filter.JwtRequestFilter;
import com.nature.common.security.jwt.provider.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	AuthenticationManager authenticationManager;
	
	// JWT 토큰을 제공하는 JwtTokenProvider 필드 선언
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsService customUserDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;
	
	
	
	@Bean
    public AuthenticationManager authenticationManager() throws Exception 
    {
        return authenticationConfiguration.getAuthenticationManager();
    }


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
		log.info("security config ...");
		
		
		
		http.cors()
		     .and()
		     .csrf(AbstractHttpConfigurer::disable)
			 .addFilterAt(new JwtAuthenticationFilter(authenticationManager(),  jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
	         .addFilterBefore(new JwtRequestFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) //사용자 정의 인증 필터추가
	         .sessionManagement(sessionManagement
	                 -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	         )
	         .authorizeHttpRequests((authorize) -> authorize
	        		 .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
	                 .requestMatchers("/*").permitAll()
	                 .requestMatchers("/users/**").permitAll()
	                 .requestMatchers("/image/**").permitAll()
	                 .requestMatchers("/items/**").permitAll()
	                 .requestMatchers("/useritems/**").permitAll()
	                 .requestMatchers("/test/**").permitAll()
	        		 .requestMatchers("/admin/**").hasAuthority("ADMIN")
	        	    // .requestMatchers(HttpMethod.GET, "/image/**").permitAll()
	        		 //.requestMatchers(HttpMethod.POST, "/image/**").hasAnyRole("MEMBER")
	        		 //.requestMatchers(HttpMethod.PUT, "/image/**").hasAnyRole("MEMBER")
	        		 //.requestMatchers(HttpMethod.DELETE, "/image/**").hasAnyRole("MEMBER")
	        		 .anyRequest().authenticated()
	        		
	         );
	        
		
		return http.build();
		
		
	}	
	


	//비밀번호 암호처리기 생성
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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
