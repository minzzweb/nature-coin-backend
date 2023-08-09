package com.nature.controller.Image;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nature.domain.Category;
import com.nature.domain.Image;
import com.nature.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:8081", "http://localhost:3000"})
@RequestMapping("/image")
public class ImageController {

	private final ImageService imageService;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	
	//이미지 게시글 등록
	@PostMapping
	public ResponseEntity<Image> register(@RequestPart("image") String imageString, 
			@RequestPart("file") MultipartFile picture) throws Exception{
		
		log.info("imageString:" + imageString);
		
		 Image image = new ObjectMapper().readValue(imageString, Image.class);
		 
		 String imagetitle = image.getImageTitle();
		 String imageContent = image.getImageContent();
		 String imageWriter = image.getImageWriter(); //임시  ->로그인한 유저가 자동 저장 되어야할 것 같은데..?
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//String imageWriter = authentication.getName(); // 현재 로그인한 사용자명
		 Category categoryId = image.getCategoryId(); 
		 
		 image.setImageTitle(imagetitle);
		 image.setImageContent(imageContent);
		 image.setImageWriter(imageWriter);
		 image.setCategoryId(categoryId);
		 image.setPicture(picture);
		
		 MultipartFile file = image.getPicture();
		 
		 log.info("originalName: " + file.getOriginalFilename());
		 log.info("size: " + file.getSize());
		 log.info("contentType: " + file.getContentType());
		 
		 String createdFileName = uploadFile(file.getOriginalFilename(), file.getBytes());
		 
		 image.setPictureUrl(createdFileName);
		 
		 this.imageService.register(image);
		 
		 log.info("register image.getImageId() = " + image.getImageId());
		 
		 Image createdImage = new Image();
		 createdImage.setImageId(image.getImageId());
		 
		return new ResponseEntity<>(createdImage,HttpStatus.OK);
	}


	private String uploadFile(String originalFilename, byte[] fileData) throws IOException {
		UUID uid = UUID.randomUUID();
		String createdFileName = uid.toString() + "_" + originalFilename;
		File target = new File(uploadPath, createdFileName);

		FileCopyUtils.copy(fileData, target);

		return createdFileName;
	}

	

	private void getCategory() {
		// TODO Auto-generated method stub
		//이미지 게시글 목록
		log.info("test");
		
	}
	
	
	
	//이미지 게시글 상세 
	
	//이미지 게시글 삭제
	
}
