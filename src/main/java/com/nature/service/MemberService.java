package com.nature.service;

import com.nature.domain.Member;

public interface MemberService {

	public void register(Member member) throws Exception;

	public Member read(Long userNo) throws Exception;


	public void modifyNickname(Member member) throws Exception;

	public long countAll() throws Exception;

	public void setupAdmin(Member member) throws Exception;

	
}
