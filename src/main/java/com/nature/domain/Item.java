package com.nature.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;
	
	@Column(length = 50)
	private String itemName;
	
	private Integer itemPrice;
	
	@Column(length = 250)
	private String itemCotent;
	
	@Transient
	private List<MultipartFile> pictures;
	
	@Column(length = 200)
	private String pictureUrl;

	@Column(length = 200)
	private String pictureUrl2;
	
	@Column(length = 200)
	private String pictureUr3;
	
	
}
