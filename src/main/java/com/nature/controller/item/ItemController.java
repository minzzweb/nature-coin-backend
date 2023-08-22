package com.nature.controller.item;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nature.common.security.domain.CustomUser;
import com.nature.domain.Item;
import com.nature.service.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:8081", "http://localhost:3000"})
@RequestMapping("/test")
public class ItemController {
	
         @GetMapping
         public ResponseEntity<String> test (@AuthenticationPrincipal CustomUser customUser){
        	 log.info("CustomUser customUser" +"test" );
        	 log.info("CustomUser customUser" +customUser.getEmail() );
        	 
        	 
        	 System.out.println("머지 ㅠㅠㅠ");
        	 
        	 
        	 return new ResponseEntity<>("성공", HttpStatus.OK);
        	 
        	

         }
}

	