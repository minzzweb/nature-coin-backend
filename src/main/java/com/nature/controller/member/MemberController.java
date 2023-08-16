package com.nature.controller.member;

import java.util.Locale;
import java.util.Locale;
import java.util.Map;


import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nature.domain.Member;
import com.nature.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class MemberController {

	private final MemberService service;
	
	//비밀번호 암호 처리기
	private final PasswordEncoder passwordEncoder;
	
	//메세지 소스 필드
	private final MessageSource messageSource;
	
	//회원등록 
	@PostMapping
	public ResponseEntity<Member> register(@Validated @RequestBody Member member) throws Exception {
		
		log.info("member.getEmail() = " + member.getEmail());
		
		String inputPassword = member.getPassword();
		member.setPassword(passwordEncoder.encode(inputPassword));
		
		service.register(member);
		
		return new ResponseEntity<>(member, HttpStatus.OK);
	}
	
	//회원 상세 
	@GetMapping("/{userNo}")
	public ResponseEntity<Member> read(@PathVariable("userNo") Long userNo) throws Exception {
		Member member = service.read(userNo);

		return new ResponseEntity<>(member, HttpStatus.OK);
	}
	
	//회원 닉네임 수정
	@PutMapping("/{userNo}")
	public ResponseEntity<Member> modifyNickname(@PathVariable("userNo") Long userNo, @RequestBody Map<String, String> nicknameMap) throws Exception {
		log.info("modify : userNo = " + userNo);
		
		String newNickname = nicknameMap.get("newNickname");

		 Member member = new Member();
		 member.setUserNo(userNo);
		 member.setNickname(newNickname);
		 service.modifyNickname(member);

		return new ResponseEntity<>(member, HttpStatus.OK);
	}
	
	//회원 프로필사진 수정 
	/*
	 * @PutMapping("/profile/{userNo}") public ResponseEntity<Member>
	 * modifyProfile(@PathVariable("userNo") Long userNo, @Validated @RequestBody
	 * Member member) throws Exception { log.info("modify : member.getEmail() = " +
	 * member.getEmail()); log.info("modify : userNo = " + userNo);
	 * 
	 * member.setUserNo(userNo);
	 * 
	 * 
	 * return new ResponseEntity<>(member, HttpStatus.OK); }
	 */
	
	
	//최초 관리자 생성 
	@PostMapping(value = "/setup", produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> setupAdmin(@Validated @RequestBody Member member)throws Exception {
		log.info("setupAdmin : member.getUserName() = " + member.getEmail());
		log.info("setupAdmin : service.countAll() = " + service.countAll());
		
		//회원의 존재여부 
		if(service.countAll() == 0) {
			String inputPassword = member.getPassword();
			member.setPassword(passwordEncoder.encode(inputPassword));
			service.setupAdmin(member);
			return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
		}
		
		//최초 관리 생성 불가 메세지 
		String message = messageSource.getMessage("common.cannotSetupAdmin", null, Locale.KOREAN);
		
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
	
	
	
}
