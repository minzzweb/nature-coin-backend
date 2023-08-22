package com.nature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nature.domain.Category;
import com.nature.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{

	 List<Image> findAllByCategoryId(Category category);

	 List<Image> findByImageWriter(String nickname);



}
