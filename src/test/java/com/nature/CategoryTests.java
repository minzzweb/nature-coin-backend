package com.nature;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nature.domain.Category;
import com.nature.domain.Image;
import com.nature.repository.CategoryRepository;
import com.nature.repository.ImageRepository;

@SpringBootTest
public class CategoryTests {
	
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ImageRepository  imageRepository;
	// 등록 테스트 
//	@Test
//	public void testRegister() {
//		
//		//카테고리 등록
//		Category category1 = new Category();
//		category1.setCategoryId("1"); 
//		category1.setCategoryName("Transportation");
//		categoryRepository.save(category1);
//		
//		
//		Category category2 = new Category();
//		category2.setCategoryId("2"); 
//		category2.setCategoryName("Cage");
//		categoryRepository.save(category2);
//		
//		//image게시판 등록 
//		Image image1 = new Image();
//		image1.setImageTitle("title1");
//		image1.setImageWriter("write1");
//		image1.setPictureUrl("123");
//		image1.setImageContent("content1");
//		image1.setCategoryId(category1);
//		
//		imageRepository.save(image1);
//		
//		Image image2 = new Image();
//		image2.setImageTitle("title2");
//		image2.setImageWriter("write2");
//		image2.setPictureUrl("123");
//		image2.setImageContent("content2");
//		image2.setCategoryId(category2);
//		
//		imageRepository.save(image2);
//	}
	//카테고리 별 가져오기 
	
	
	@Test
	public void  testList() {
		
		//List<Category> Categories =  categoryRepository.findAll();
		
		//for(Category Category : Categories) {
		//	System.out.println("Category.getCategoryId() " + Category.getCategoryId());
		//	System.out.println("Category.getCategoryName()" +  Category.getCategoryName());
		//}
		
		
		Category category = categoryRepository.findByCategoryName("Food").get();
		
		System.out.println("category.getCategoryId( )= "  + category.getCategoryId());
		System.out.println("category.getCategoryName( )= "  + category.getCategoryName());
	
		  List<Image> imagesInCategory = imageRepository.findAllByCategoryId(category);
		  
		for (Image image : imagesInCategory) {
	        System.out.println("Image ID: " + image.getImageId());
	        System.out.println("Image Title: " + image.getImageTitle());
	      
	    }
		
	}
	
	
	
	
}
