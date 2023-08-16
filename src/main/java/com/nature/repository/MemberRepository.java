package com.nature.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nature.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	
}
