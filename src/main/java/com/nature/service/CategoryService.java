package com.nature.service;

import java.util.Optional;

import com.nature.domain.Category;

public interface CategoryService {


	public Optional<Category> findById(String categoryId) throws Exception;

	public Optional<Category> findByCategoryName(String category) throws Exception;

	
	
}
