package com.nature.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
	public void modify(Image image) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long imageId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Image> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPicture(Long itemId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
