package com.nature.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nature.domain.Category;
import com.nature.domain.Image;
import com.nature.repository.ImageRepository;

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

	@Override
	public List<Image> list() throws Exception {
		
		Sort sort = Sort.by(Sort.Direction.DESC, "imageId");
        Pageable pageable = PageRequest.of(0, 12, sort);
        List<Image> recentImages = repository.findAll(pageable).getContent();
        return recentImages;
	}

	@Override
	@Transactional
	public void modifyWriter(String oldNickname, String newNickname) throws Exception {
		
		List<Image> Images =  repository.findByImageWriter(oldNickname);
		
		for(Image image : Images) {
			image.setImageWriter(newNickname);
			
			repository.save(image);
		}
		
	}

	@Override
	public List<Image> mypageImagelist(String imageWriter) throws Exception {
		
		return repository.findByImageWriter(imageWriter);
	}

	


}
