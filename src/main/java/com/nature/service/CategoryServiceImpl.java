package com.nature.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nature.domain.Category;
import com.nature.repository.CategoryRepository;
import com.nature.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository repository;



	@Override
	public Optional<Category> findById(String categoryId) throws Exception {
		
		return repository.findById(categoryId);
	}


	@Override
	public Optional<Category> findByCategoryName(String categoryName) throws Exception {

		return repository.findByCategoryName(categoryName);
	}

	

}
