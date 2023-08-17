package com.nature.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nature.common.security.domain.CustomUser;
import com.nature.domain.Member;
import com.nature.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

//사용자 정의 유저 상세 클래스
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MemberRepository repository;

	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("email : " + email);
		
		Member member = repository.findByEmail(email); //사용자 아이디로 사용자 정보조회 
		
		if (member == null) {
		    throw new UsernameNotFoundException("User not found with email: " + email);
		}
		
		log.info("member : " + member);

		return member == null ? null : new CustomUser(member);
	}
	

	
}
