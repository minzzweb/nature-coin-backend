package com.nature.service;


import java.util.List;
import com.nature.domain.Image;

public interface ImageService {

	public void register(Image image) throws Exception;
	
	public Image read(Long imageId) throws Exception;

	public void modify(Image image) throws Exception;

	public void remove(Long imageId) throws Exception;

	public List<Image> list() throws Exception;

	public String getPicture(Long itemId) throws Exception;
	
}
