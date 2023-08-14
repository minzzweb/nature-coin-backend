package com.nature.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nature.domain.Category;
import com.nature.domain.Image;
import com.nature.repository.ImageRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

	private final ImageRepository repository;
	
	@Override
	@Transactional
	public void register(Image image) throws Exception {
		repository.save(image);	
	}

	@Override
	@Transactional(readOnly = true)
	public Image read(Long imageId) throws Exception {
		return repository.getOne(imageId);
	}
	
	@Override
	public String getPicture(Long imageId) throws Exception {
		
		Image image =  repository.getOne(imageId);
		return image.getPictureUrl();
	}
	
	
	@Override
	@Transactional
	public void modify(Image image) throws Exception {
		Image imageEntity = repository.getOne(image.getImageId());
		
		imageEntity.setImageTitle(image.getImageTitle());
		imageEntity.setImageContent(image.getImageContent());
		imageEntity.setPictureUrl(image.getPictureUrl());
		
	}

	@Override
	@Transactional
	public void remove(Long imageId) throws Exception {
		repository.deleteById(imageId);
		
	}

	@Override
	public List<Image> listByCategoryId(Category category) {
		return repository.findAllByCategoryId(category);
	}


}
