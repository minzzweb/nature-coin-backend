package com.nature.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nature.domain.Image;
import com.nature.repository.ImageRepository;

import jakarta.transaction.Transactional;
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
	public Image read(Long imageId) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
