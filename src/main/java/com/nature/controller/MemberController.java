package com.nature.controller;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nature.common.security.domain.CustomUser;
import com.nature.domain.Member;
import com.nature.service.ImageService;
import com.nature.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:3000" })
@RequestMapping("/users")
public class MemberController {

	private final MemberService memberService;
	private final ImageService imageService;
	
	@Value("${upload.path}")
	private String uploadPath;
	
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
		
		memberService.register(member);
		
		return new ResponseEntity<>(member, HttpStatus.OK);
	}
	
	//회원 상세 
	@GetMapping("/{userNo}")
	public ResponseEntity<Member> read(@PathVariable("userNo") Long userNo) throws Exception {
		Member member = memberService.read(userNo);
			
		return new ResponseEntity<>(member, HttpStatus.OK);
	}
	
	
	
    //회원 프로필 수정
	@PutMapping
	public ResponseEntity<Member> modify(@RequestPart("member") String memberString, 
			@RequestPart(name = "file", required = false) MultipartFile picture,@AuthenticationPrincipal CustomUser customUser) throws Exception { 
		
		  log.info("modify : memberString = " + memberString);
		  log.info("회원 상세 " + customUser.getEmail());
		  
		  Member member = new ObjectMapper().readValue(memberString, Member.class);
		  
		  String oldNickname = memberService.findNickNamebyEmail(customUser.getEmail());
		  log.info("닉네임 변경이전 닉네임 가져오기  " + oldNickname);
		  
		  if(picture!=null) {
			  
			  member.setPicture(picture);
		      MultipartFile file = member.getPicture(); 
		      
		      log.info("originalName:" + file.getOriginalFilename()); 
		      log.info("size : " + file.getSize() );
		      log.info("contentType :" + file.getContentType());
		  
			  String createdFileName = uploadFile(file.getOriginalFilename(),file.getBytes());
			  log.info("modify : createdFileName = " + createdFileName);
			  
			  member.setPictureUrl(createdFileName); 
			  
			  }
		  else { 
			  Member oldMember = this.memberService.read(member.getUserNo());
			  member.setPictureUrl(oldMember.getPictureUrl());
			  log.info("oldMember.getPictureUrl() :" + oldMember.getPictureUrl());
	  
		  }
		  
		      this.memberService.modify(member);
		      //닉네임 수정시 게시판 작성자도 변경되어야함.
		      String newNickname = member.getNickname();
		      this.imageService.modifyWriter(oldNickname, newNickname);
		      
		      Member modifiedMember = new Member();
	
		      modifiedMember.setUserNo(member.getUserNo());
		 
	   return new ResponseEntity<>(modifiedMember, HttpStatus.OK); 
	}
	 
	
	
	private String uploadFile(String originalFilename, byte[] fileData) throws IOException {
		UUID uid = UUID.randomUUID();
		String createdFileName = uid.toString() + "_" + originalFilename;
		File target = new File(uploadPath, createdFileName);

		FileCopyUtils.copy(fileData, target);

		return createdFileName;
	}
	
	private MediaType getMediaType(String formatName) {

		if (formatName != null) {
			if (formatName.equals("JPG")) {
				return MediaType.IMAGE_JPEG;
			}

			if (formatName.equals("GIF")) {
				return MediaType.IMAGE_GIF;
			}

			if (formatName.equals("PNG")) {
				return MediaType.IMAGE_PNG;
			}
		}

		return null;
	}
	
	
	//최초 관리자 생성 
	@PostMapping(value = "/setup", produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> setupAdmin(@Validated @RequestBody Member member)throws Exception {
		log.info("setupAdmin : member.getUserName() = " + member.getEmail());
		log.info("setupAdmin : service.countAll() = " + memberService.countAll());
		
		//회원의 존재여부 
		if(memberService.countAll() == 0) {
			String inputPassword = member.getPassword();
			member.setPassword(passwordEncoder.encode(inputPassword));
			memberService.setupAdmin(member);
			return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
		}
		
		//최초 관리 생성 불가 메세지 
		String message = messageSource.getMessage("최초관리자를 등록할 수 없습니다.", null, Locale.KOREAN);
		
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
	
	@GetMapping("/myinfo")
	public ResponseEntity<Member> getMyInfo(@AuthenticationPrincipal CustomUser customUser) throws Exception {
		
		Long userNo = customUser.getUserNo();
		log.info("register userNo = " + userNo);

		Member member = memberService.read(userNo);

		member.setPassword("");

		return new ResponseEntity<>(member, HttpStatus.OK);
		
	}
	
	// 이미지 사진
	// 가져오기===================================================================
	@GetMapping("/display")
	public ResponseEntity<byte[]> displayFile(String email) throws Exception {

			ResponseEntity<byte[]> entity = null;

			String fileName = memberService.getPicture(email);

			log.info("FILE NAME: " + fileName);
			
			if(fileName!=null) {
				try {
					String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
					MediaType mediaType = getMediaType(formatName);
					HttpHeaders headers = new HttpHeaders();
					File file = new File(uploadPath + File.separator + fileName);

					if (mediaType != null) {
						headers.setContentType(mediaType);
					}
					entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.CREATED);
				} catch (Exception e) {
					e.printStackTrace();
					entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
				}
				return entity;
			}else {
				return null;
			}
						
		}	
	

		
}
