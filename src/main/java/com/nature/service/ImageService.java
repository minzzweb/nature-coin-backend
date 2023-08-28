package com.nature.service;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.nature.domain.Category;
import com.nature.domain.Image;
import com.nature.dto.PageRequestVO;

public interface ImageService {

	public void register(Image image) throws Exception;
	
	public Image read(Long imageId) throws Exception;
	
	public String getPicture(Long itemId) throws Exception;
	
	public void modify(Image image) throws Exception;

	public void remove(Long imageId) throws Exception;

	public Page<Image> listByCategoryId(Category category,PageRequestVO pageRequestVO ) throws Exception;

	public Page<Image> list(PageRequestVO pageRequestVO ) throws Exception;

	public void modifyWriter(String oldNickname, String nickname) throws Exception;

	public Page<Image> mypageImagelist(String imageWriter,PageRequestVO pageRequestVO) throws Exception;




	
}
