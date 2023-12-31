package com.nature.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nature.domain.Member;
import com.nature.domain.MemberAuth;
import com.nature.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberRepository repository;

	//회원 등록
	@Override
	public void register(Member member) {
		
		Member memberEntity = new Member();
		memberEntity.setEmail(member.getEmail());
		memberEntity.setNickname(member.getNickname());
		memberEntity.setPassword(member.getPassword());
		
		MemberAuth memberAuth = new MemberAuth();
		memberAuth.setAuth("ROLE_MEMBER");
		
		memberEntity.addAuth(memberAuth); //권환등록
		
		repository.save(memberEntity);
		
		member.setUserNo(memberEntity.getUserNo());
	}

	//회원 상세
	@Override
	public Member read(Long userNo) throws Exception {
		return repository.getOne(userNo);
	}

	//회원 수정 
	@Override
	public void modify(Member member) throws Exception {
		Member memberEntity = repository.getOne(member.getUserNo());
		memberEntity.setNickname(member.getNickname());
		memberEntity.setPictureUrl(member.getPictureUrl());
		
		repository.save(memberEntity);
	}

	//회원 데이터 수 
	@Override
	public long countAll() throws Exception {
		
		return repository.count();
	}

	//관리자 생성
	@Override
	public void setupAdmin(Member member) throws Exception {
		Member memberEntity = new Member();
		memberEntity.setEmail(member.getEmail());
		memberEntity.setNickname(member.getNickname());
		memberEntity.setPassword(member.getPassword());
		
		MemberAuth memberAuth = new MemberAuth();
		memberAuth.setAuth("ROLE_ADMIN");
		
		memberEntity.addAuth(memberAuth);
		
		repository.save(memberEntity);
	}

	
	@Override
	public String getPicture(String email) throws Exception {
		
		Member member =  repository.findByEmail(email);
		return member.getPictureUrl();
	}

	@Override
	public String findNickNamebyEmail(String email) throws Exception {
		
		Member member = repository.findByEmail(email);
		String nickname = member.getNickname();
		
		return nickname;
	}

	@Override
	public Member findbyNickname(String imageWriter) throws Exception {
	
		return repository.findByNickname(imageWriter);
	}

	@Override
	public int getCoin(Long userNo) throws Exception {
		
		Member member = repository.getOne(userNo);
		return member.getCoin();
	}
	
	//아이디 중복여부 
	@Transactional(readOnly = true)
	@Override
	public boolean checkNicknameDuplication(String nickname) throws Exception {
		boolean usernameDuplicate = repository.existsByNickname(nickname); //있으면 true
		
		return usernameDuplicate;
	}
	
}
