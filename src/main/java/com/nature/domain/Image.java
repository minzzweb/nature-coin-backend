package com.nature.domain;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of="imageId")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@ToString
@Entity
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imageId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="categoryId",nullable = false)
	private Category categoryId;
	
	@Column(length = 255, nullable = false)
	private String imageTitle;
	
	@Transient
	private MultipartFile picture;
	
	@Column(length = 255, nullable = false)
	private String pictureUrl;
	
	@Column(length = 255, nullable = false)
	private String imageContent;
	
	@Column(length = 255, nullable = false)
	private String imageWriter;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@CreationTimestamp
	@Column(name = "reg_date")
	private LocalDateTime regDate;
	
}
