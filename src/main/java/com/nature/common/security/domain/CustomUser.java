package com.nature.common.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.nature.domain.Member;

public class CustomUser extends User{
	
	private static final long serialVersionUID = 1L;
	
	private Member member;
	
	public CustomUser(String email, String password, Collection<? extends GrantedAuthority> authorities) {
		super(email, password, authorities);
		}
	
	public CustomUser(Member member) {
		super(member.getEmail(),member.getPassword(),member.getAuthList().stream().map(auth->new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
		
        this.member = member;
	}
	
	public CustomUser(Member member, Collection<? extends GrantedAuthority> authorities) {
		super(member.getEmail(),member.getPassword(), authorities);
		this.member = member;
	}
	
	public long getUserNo() {
		return member.getUserNo();
		}
	
	public String getEmail() {
		return member.getEmail();
	}
		
	
}
