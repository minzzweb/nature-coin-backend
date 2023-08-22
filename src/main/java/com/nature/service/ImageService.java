package com.nature.service;


import java.util.List;
import java.util.Optional;

import com.nature.domain.Category;
import com.nature.domain.Image;

public interface ImageService {

	public void register(Image image) throws Exception;
	
	public Image read(Long imageId) throws Exception;
	
	public String getPicture(Long itemId) throws Exception;
	
	public void modify(Image image) throws Exception;

	public void remove(Long imageId) throws Exception;

	public List<Image> listByCategoryId(Category category) throws Exception;

	public List<Image> list() throws Exception;

	public void modifyWriter(String oldNickname, String nickname) throws Exception;




	
}
