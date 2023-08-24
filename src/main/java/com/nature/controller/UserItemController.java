package com.nature.controller;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nature.common.security.domain.CustomUser;
import com.nature.domain.UserItem;
import com.nature.service.UserItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:3000" })
@RequestMapping("/useritems")
public class UserItemController {
	
	private final UserItemService service;

	//구매상품 목록
	@GetMapping
	public ResponseEntity<List<UserItem>> list(@AuthenticationPrincipal CustomUser customUser) throws Exception {
		Long userNo = customUser.getUserNo();

		log.info("*******UserItem : userNo " + userNo);

		System.out.println("service.list(userNo)" + service.list(userNo));
		
		return new ResponseEntity<>(service.list(userNo), HttpStatus.OK);
	}
}
