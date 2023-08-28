package com.nature.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nature.domain.Category;
import com.nature.domain.Image;
import com.nature.dto.PageRequestVO;
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
	public Page<Image> listByCategoryId(Category category,PageRequestVO pageRequestVO )throws Exception {
		
		int pageNumber = pageRequestVO.getPage() - 1;
		int sizePerPage = pageRequestVO.getSizePerPage();
		
		Pageable pageRequest = PageRequest.of(pageNumber, sizePerPage, Sort.Direction.DESC, "imageId");
		
		Page<Image> page = repository.findAllByCategoryId(category,pageRequest);
		
		return page;
	 
	}

	@Override
	public Page<Image> list(PageRequestVO pageRequestVO) throws Exception {
		
		int pageNumber = pageRequestVO.getPage() - 1;
		int sizePerPage = pageRequestVO.getSizePerPage();
		
		Pageable pageRequest = PageRequest.of(pageNumber, sizePerPage, Sort.Direction.DESC, "imageId");
		
		Page<Image> page = repository.findAll(pageRequest);
        return page;
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
	public Page<Image> mypageImagelist(String imageWriter,PageRequestVO pageRequestVO) throws Exception {
		
		int pageNumber = pageRequestVO.getPage() - 1;
		int sizePerPage = pageRequestVO.getSizePerPage();
		Pageable pageRequest = PageRequest.of(pageNumber, sizePerPage, Sort.Direction.DESC, "imageId");
		
		Page<Image> page =  repository.findByImageWriter(imageWriter,pageRequest);
		
		return page;
	}

	


}
