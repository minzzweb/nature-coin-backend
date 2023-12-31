package com.nature.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nature.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{


	public Member findByEmail(String email);

	public Member findByNickname(String imageWriter);


	//닉네임 중복 여부 
	boolean existsByNickname(String nickname);
	
	
}
