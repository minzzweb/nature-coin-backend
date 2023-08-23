package com.nature.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nature.common.security.domain.CustomUser;
import com.nature.domain.Category;
import com.nature.domain.Image;
import com.nature.domain.Member;
import com.nature.service.CategoryService;
import com.nature.service.ImageService;
import com.nature.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:3000" })
@RequestMapping("image")
public class ImageController {

	private final ImageService imageService;
	private final CategoryService categoryService;
	private final MemberService memberService;

	@Value("${upload.path}")
	private String uploadPath;

	// 이미지 게시글 등록
	// ===================================================================
	
    //@PreAuthorize("hasRole('MEMBER')")
	@PostMapping
	public ResponseEntity<Image> register(@RequestPart("image") String imageString,
			@RequestPart("file") MultipartFile picture, @RequestPart("categoryId") String categoryId,
			@AuthenticationPrincipal CustomUser customUser
			) throws Exception {

		log.info("imageString:" + imageString);
		

		Image image = new ObjectMapper().readValue(imageString, Image.class);

		String imageContent = image.getImageContent();
		String imagetitle = image.getImageTitle();	
		String imageWriter = memberService.findNickNamebyEmail(customUser.getEmail());		   
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

		this.imageService.register(image); // 등록!
		log.info("register image.getImageId() = " + image.getImageId());

		Image createdImage = new Image();

		createdImage.setImageId(image.getImageId());

		return new ResponseEntity<>(createdImage, HttpStatus.OK);

	}

	private String uploadFile(String originalFilename, byte[] fileData) throws IOException {
		UUID uid = UUID.randomUUID();
		String createdFileName = uid.toString() + "_" + originalFilename;
		File target = new File(uploadPath, createdFileName);

		FileCopyUtils.copy(fileData, target);

		return createdFileName;
	}

	// 이미지 게시글 상세
	// ===================================================================
	@GetMapping("/{imageId}")
	public ResponseEntity<Map<String, Object>> read(@PathVariable("imageId") Long imageId) throws Exception {

		log.info("read");
		log.info("imageId = " + imageId);
		

		Image image = this.imageService.read(imageId);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("image", image);
		responseData.put("categoryName", image.getCategoryId().getCategoryName());

		return new ResponseEntity<>(responseData, HttpStatus.OK);

	}

	// 이미지 사진
	// 가져오기===================================================================
	@GetMapping("/display")
	public ResponseEntity<byte[]> displayFile(Long imageId) throws Exception {

		ResponseEntity<byte[]> entity = null;

		String fileName = imageService.getPicture(imageId);

		log.info("FILE NAME: " + fileName);

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
	}

	//게시판 작성자 프로필 가져오기 
		// 가져오기===================================================================
		@GetMapping("/display/profile")
		public ResponseEntity<byte[]> displayProfileFile(String imageWriter) throws Exception {

			ResponseEntity<byte[]> entity = null;

			log.info("imageWriter " + imageWriter);
			
			//작성자를 member서비스에서 닉네임으로 찾아서 그 이메일을 가지고 와야함
			Member member =  memberService.findbyNickname(imageWriter);
			String fileName = memberService.getPicture(member.getEmail());

			log.info("FILE NAME: " + fileName);

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
	

	

	// 이미지 게시판 수정
	// ===================================================================
	@PutMapping
	public ResponseEntity<Image> modify(@RequestPart("image") String imageString,
			@RequestPart(name = "file", required = false) MultipartFile picture) throws Exception {

		log.info("imageString: " + imageString);

		Image image = new ObjectMapper().readValue(imageString, Image.class);
	
		  String imagetitle = image.getImageTitle(); 
		  String imageContent=image.getImageContent();
		  
		  image.setImageTitle(imagetitle); 
		  image.setImageContent(imageContent);
		  
		  if(picture!=null) {
			  image.setPicture(picture);
		      MultipartFile file = image.getPicture(); 
		      
		      log.info("originalName:" + file.getOriginalFilename()); 
		      log.info("size : " + file.getSize() );
		      log.info("contentType :" + file.getContentType());
		  
			  String createdFileName = uploadFile(file.getOriginalFilename(),file.getBytes());
			  image.setPictureUrl(createdFileName); 
			  }
		
		  
		  else { 
	  
			  Image oldImage = this.imageService.read(image.getImageId());
		      image.setPictureUrl(oldImage.getPictureUrl()); 
		      
		  }
		  
		      this.imageService.modify(image);
			  Image modifiedImage = new Image();
			  modifiedImage.setImageId(image.getImageId());
 
	    	return new ResponseEntity<Image>(modifiedImage, HttpStatus.OK);
	}

	
	// 이미지 게시판 삭제
	// ===================================================================
	@DeleteMapping("/{imageId}")
	public ResponseEntity<Void> remove(@PathVariable("imageId") Long imageId) throws Exception {
		log.info("remove");

		this.imageService.remove(imageId);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	
	// 이미지 게시판 목록
	// ===================================================================
	
	@GetMapping("/list/{categoryName}") 
	public ResponseEntity<List<Image>> list(@PathVariable("categoryName") String categoryName) throws Exception {
	     
		log.info("categoryName = " + categoryName);
	    
	    Category categoryEntity = categoryService.findByCategoryName(categoryName).get();
	   
		List<Image> imageList = this.imageService.listByCategoryId(categoryEntity);
		 
		log.info("imageList = " + imageList);
		
		return new ResponseEntity<>(imageList,HttpStatus.OK);
		

	}
	
	// 메인 이미지 목록
	// ===================================================================
	@GetMapping
	public ResponseEntity<List<Image>> list() throws Exception {
		
		log.info("main list");
		List<Image> ImageList = this.imageService.list();
		
		System.out.println("ImageList" +ImageList);

	   return new ResponseEntity<>(ImageList, HttpStatus.OK);
		
	}
	
	// 마이페이지 이미지 목록
    // ===================================================================
	@GetMapping("/mypage/list/myimage/{imageWriter}")
	public ResponseEntity<List<Image>> mypageImageList(@PathVariable("imageWriter") String imageWriter) throws Exception{
		
		log.info("mypageImage list : imageWriter " + imageWriter);
		List<Image> mypageImageList = this.imageService.mypageImagelist(imageWriter);
		
		for(Image images : mypageImageList) {
			System.out.println(images.getImageTitle());
		}
		
		return new ResponseEntity<>(mypageImageList, HttpStatus.OK);
	} 
	
		

	
}
