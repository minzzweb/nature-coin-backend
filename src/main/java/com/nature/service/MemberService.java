package com.nature.service;

import com.nature.domain.Member;

public interface MemberService {

	public void register(Member member) throws Exception;

	public Member read(Long userNo) throws Exception;

	public void modify(Member member) throws Exception;

	public long countAll() throws Exception;

	public void setupAdmin(Member member) throws Exception;

	public String getPicture(String email)  throws Exception;

	public String findNickNamebyEmail(String email) throws Exception;

	public Member findbyNickname(String imageWriter) throws Exception;

	public int getCoin(Long userNo) throws Exception;

	//아이디 중복여부 
	public boolean checkNicknameDuplication(String nickname) throws Exception;

	
}
