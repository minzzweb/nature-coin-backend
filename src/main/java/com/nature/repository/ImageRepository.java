package com.nature.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nature.domain.Category;
import com.nature.domain.Image;
import com.nature.dto.PageRequestVO;

public interface ImageRepository extends JpaRepository<Image, Long>{

	 //List<Image> findAllByCategoryId(Category category);

	 List<Image> findByImageWriter(String nickname);

	 Page<Image> findAllByCategoryId(Category category, Pageable pageRequest);

	 Page<Image> findByImageWriter(String imageWriter, Pageable pageRequest);



}
