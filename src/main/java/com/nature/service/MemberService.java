package com.nature.service;

import com.nature.domain.Member;

public interface MemberService {

	public void register(Member member) throws Exception;

	public Member read(Long userNo) throws Exception;

	public void modify(Member member) throws Exception;

	public long countAll() throws Exception;

	public void setupAdmin(Member member) throws Exception;

	public String getPicture(Long userNo)  throws Exception;

	

	
}
