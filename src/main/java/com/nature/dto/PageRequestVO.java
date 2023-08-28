package com.nature.dto;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageRequestVO {

	private int page; //요청한 페이지 번호
	private int sizePerPage; //페이지당 아이템 개수
	
	public PageRequestVO() {
		this.page = 1; 
		this.sizePerPage = 12;
	}
	
	//요청한 페이지 번호를 설정하는 메서드 
	public void setPage(int page) {
		
		if (page <= 0) { //페이지 번호가 0이하면 기본값으로 설정하고 반환 
		this.page = 1;
		
		return;
	}

		this.page = page;
	}
	
	//페이지당 아이템 개수를 설정하는 메서드, 
	public void setSizePerPage(int size) {
		if (size <= 0 || size > 100) {
		this.sizePerPage = 12;
		
		return;
	}

		this.sizePerPage = size;
	}
	
	//페이지 번호를 반환하는 메서드 
	public int getPage() {
		return page;
	}
	
	//페이지의 시작 아이템 인덱스를 반환하는 메서드 
	public int getPageStart() {
		return (this.page - 1) * sizePerPage;
	}
	
	//페이지당 아이템 개수를 반환하는 메서드 
	public int getSizePerPage() {
		return this.sizePerPage;
	}
	
	//페이지 요청 정보를 URL 문자열로 반환하는 메서드 
	public String toUriString() {
		
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
		.queryParam("page", this.page)
		.queryParam("size", this.sizePerPage)
		.build();

		return uriComponents.toUriString();
	}
}
