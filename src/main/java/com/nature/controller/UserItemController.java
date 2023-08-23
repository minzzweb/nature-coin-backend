package com.nature.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
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
@RequestMapping("/useritems")
public class UserItemController {
	
	private final UserItemService service;

	//구매상품 목록
	@GetMapping
	public ResponseEntity<List<UserItem>> list(@AuthenticationPrincipal CustomUser customUser) throws Exception {
		Long userNo = customUser.getUserNo();

		log.info("read : userNo " + userNo);

		return new ResponseEntity<>(service.list(userNo), HttpStatus.OK);
	}
}
