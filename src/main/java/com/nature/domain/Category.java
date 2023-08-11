package com.nature.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of="categoryId")
@Entity
public class Category {
	
	 @Id
	 private String categoryId;
	 private String categoryName;
	 @CreationTimestamp
	 private LocalDateTime regDate;
	 
	
}
