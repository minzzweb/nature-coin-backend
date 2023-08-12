package com.nature.controller.Image;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nature.domain.Category;
import com.nature.domain.Image;
import com.nature.service.CategoryService;
import com.nature.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:8081", "http://localhost:3000"})
@RequestMapping("image")
public class ImageController {

	

	private final ImageService imageService;
	private final CategoryService categoryService;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	
	//이미지 게시글 등록 ===================================================================
	@PostMapping
	public ResponseEntity<Image> register(@RequestPart("image") String imageString, 
			@RequestPart("file") MultipartFile picture,@RequestPart("categoryId") String categoryId 
			) throws Exception{
		
		log.info("imageString:" + imageString);
		
		 Image image = new ObjectMapper().readValue(imageString, Image.class);
		 
		 String imageContent = image.getImageContent();
		 String imagetitle = image.getImageTitle();
		 //String imageWriter = image.getImageWriter(); //임시  ->로그인한 유저가 자동 저장 되어야할 것 같은데..?
		 //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 //String imageWriter = authentication.getName(); // 현재 로그인한 사용자명
		 String imageWriter ="chominjoo";
		 Category category = categoryService.findById(categoryId).get();
		  
		image.setImageContent(imageContent);
		image.setImageTitle(imagetitle);
		image.setImageWriter(imageWriter);
		image.setPicture(picture);
		image.setCategoryId(category);
		
		MultipartFile file = image.getPicture();	 
		log.info("originalName: " + file.getOriginalFilename());
		log.info("size: " + file.getSize());
		log.info("contentType: " + file.getContentType()); 
		String createdFileName = uploadFile(file.getOriginalFilename(), file.getBytes());
		image.setPictureUrl(createdFileName);

		this.imageService.register(image); //등록!
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
	

	//이미지 게시글 상세 ===================================================================
	@GetMapping("/{imageId}")
	public ResponseEntity<Image> read(@PathVariable("imageId") Long imageId) throws Exception {
	   
		log.info("read" );
		log.info("imageId = " + imageId );
		
		Image image = this.imageService.read(imageId);
		System.out.println("image  = " + image );
		
	   return new ResponseEntity<>(image, HttpStatus.OK);
	
	}

	
	
//	//이미지 게시글 삭제
//	@DeleteMapping("/{itemId}")
//	public ResponseEntity<Void> remove(@PathVariable("itemId") Long itemId) throws Exception {
//	log.info("remove");
//
//	this.itemService.remove(itemId);
//
//	return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//	}
	
	
	
	//사진 미리보기 
//	@GetMapping("/display")
//	public ResponseEntity<byte[]> displayFile(Long itemId) throws Exception {
//	ResponseEntity<byte[]> entity = null;
//
//	String fileName = itemService.getPicture(itemId);
//
//	log.info("FILE NAME: " + fileName);
//
//	try {
//
//	String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
//
//	MediaType mediaType = getMediaType(formatName);
//
//	HttpHeaders headers = new HttpHeaders();
//
//	File file = new File(uploadPath + File.separator + fileName);
//
//	if (mediaType != null) {
//	headers.setContentType(mediaType);
//	}
//
//	entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), 
//	
//			headers, HttpStatus.CREATED);
//	} catch (Exception e) {
//	e.printStackTrace();
//	entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
//	}
//
//	return entity;
//	}
//
//	private MediaType getMediaType(String formatName){
//	if(formatName != null) {
//	if(formatName.equals("JPG")) {
//	return MediaType.IMAGE_JPEG;
//	}
//
//	if(formatName.equals("GIF")) {
//	return MediaType.IMAGE_GIF;
//	}
//
//	if(formatName.equals("PNG")) {
//	return MediaType.IMAGE_PNG;
//	}
//	}
//
//	return null;
//	}

	
//    //게시글 목록
//	@GetMapping
//	public ResponseEntity<List<Item>> list() throws Exception {
//	log.info("list");
//	List<Item> itemList = this.itemService.list();
//	return new ResponseEntity<>(itemList, HttpStatus.OK);
//	}
//	
//	
//	
//	

//	
//	
//	//이미지 게시글 수정
//	@PutMapping
//	public ResponseEntity<Item> modify(@RequestPart("item") String itemString, @RequestPart(name = "file", required = false) MultipartFile picture) throws Exception {
//		log.info("itemString: " + itemString);
//
//		Item item = new ObjectMapper().readValue(itemString, Item.class);
//
//		String itemName = item.getItemName();
//		String description = item.getDescription();
//
//		if(itemName != null) {
//		log.info("item.getItemName(): " + itemName);
//
//		item.setItemName(itemName);
//		}
//
//		if(description != null) {
//		log.info("item.getDescription(): " + description);
//
//		item.setDescription(description);
//		}
//
//
//		if(picture != null) {
//		item.setPicture(picture);
//
//		MultipartFile file = item.getPicture();
//
//		log.info("originalName: " + file.getOriginalFilename());
//		log.info("size: " + file.getSize());
//		log.info("contentType: " + file.getContentType());
//		String createdFileName = uploadFile(file.getOriginalFilename(), file.getBytes());
//
//		item.setPictureUrl(createdFileName);
//		}
//		else {
//		Item oldItem = this.itemService.read(item.getItemId());
//		item.setPictureUrl(oldItem.getPictureUrl());
//		}
//
//		this.itemService.modify(item);
//
//		Item modifiedItem = new Item();
//		modifiedItem.setItemId(item.getItemId());
//
//		return new ResponseEntity<>(modifiedItem, HttpStatus.OK);
//		}
}
