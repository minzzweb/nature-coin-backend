package com.nature.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nature.common.security.domain.CustomUser;
import com.nature.domain.Member;
import com.nature.dto.Item;
import com.nature.service.MemberService;
import com.nature.service.UserItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:8081", "http://localhost:3000"})
@RequestMapping("/items")
public class ItemController {
	
	  private final MemberService memberService;
	  private final UserItemService userItemService;
	  private final MessageSource messageSource;
	
      //상품 구매
	  @PostMapping(value = "/buy",produces = "text/plain;charset=UTF-8")
	  public ResponseEntity<String> buy(@RequestBody Item item,@AuthenticationPrincipal CustomUser customUser ) throws Exception{
		
		  Long userNo = customUser.getUserNo();
		  
		  log.info("buy userNo =" + userNo);
		  log.info("구입 물건 : " + item.getItemId() + " ," + item.getItemName()+"," + item.getPrice());
		  	  
		  Member member = memberService.read(userNo);
		  
		  member.setCoin(memberService.getCoin(userNo));
		  
		  int coin = member.getCoin();
		  int price = item.getPrice();
		  
		  if (coin < price) {
		        String message = messageSource.getMessage("item.insufficientCoins", null, Locale.KOREAN);
		        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		   }
		  
		  userItemService.register(member, item); 
		  		  
		  String message = messageSource.getMessage("item.purchaseComplete", null, Locale.KOREAN);
		  
		  return new ResponseEntity<>(message, HttpStatus.OK);
  
	  }
}

	