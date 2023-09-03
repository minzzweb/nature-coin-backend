package com.nature.controller;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nature.common.security.domain.CustomUser;
import com.nature.domain.Image;
import com.nature.domain.UserItem;
import com.nature.dto.PageRequestVO;
import com.nature.dto.PaginationDTO;
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
	@PreAuthorize("hasAnyRole('MEMBER')")
	@GetMapping
	public ResponseEntity<PaginationDTO<UserItem>> list(@AuthenticationPrincipal CustomUser customUser,PageRequestVO pageRequestVO) throws Exception {
		Long userNo = customUser.getUserNo();

		log.info("*******UserItem : userNo " + userNo);

		Page<UserItem> userItemPage = this.service.list(userNo,pageRequestVO);
		PaginationDTO<UserItem> serItemPageDTO = new PaginationDTO<>(userItemPage); 
		
		return new ResponseEntity<>(serItemPageDTO, HttpStatus.OK);
	}
}
