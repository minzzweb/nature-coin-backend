package com.nature.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nature.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, String>{

	
	public Optional<Category> findById(String categoryId); 

}
