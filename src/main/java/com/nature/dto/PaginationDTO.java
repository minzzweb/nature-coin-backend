package com.nature.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;

@Getter
public class PaginationDTO<T> {
	
	//Spring Data에서 제공하는 Page타입 객체로 페이지 정보와 데이터를 담고 있다. 
	private Page<T> page;
	
	private Pageable prevPageable; //이전 페이지와 
	private Pageable nextPageable; //다음 페이지의 Pageable 객체를 담는다. 
	
	private int currentPageNumber; //현재 페이지 번호를 담는다.
	private int totalPageCount; //전체 페이지 개수를 담는다. 
	
	private Pageable currentPageable; //현재 페이지의 Pageable 객체를 담는다. 
	
	private List<Pageable> pageableList; //페이지 목록을 담는 리스트 
	
	//주어진 Page<T> 객체를 기반으로 `페이지네이션 정보`를 초기화 한다 
	
	public PaginationDTO(Page<T> page){
		
		//생성자가 호출되면 페이지 정보를 받아와서 필드 값을 초기화한다
		this.page = page;

		//현재 페이지의 Pageable 객체를 가져온다. 
		this.currentPageable = page.getPageable(); 
		
		//현재 페이지 번호를 계산한다. 
		this.currentPageNumber = this.currentPageable.getPageNumber() + 1; 

		//전체 페이지 개수를 가져온다. 
		this.totalPageCount = page.getTotalPages();

		//페이지 목록을 저장하기 위한 리스트를 초기화한다. 
		this.pageableList = new ArrayList<>();

		//calcPages()메서드르 호출하여 페이지 목록을 계산 
		calcPages(); 
		
	}


	private void calcPages() {
		
		//현재 페이지 번호를 기준으로 표시될 페이지 번호 범위를 계산 (끝 페이지 번호 )
		int endPageNumber = (int)(Math.ceil(this.currentPageNumber/10.0)*10);
		int startPageNumber = endPageNumber - 9;
		
		//현재 페이지의 Pageable 객체를 저장
		Pageable startPageable = this.currentPageable;
		
		//이전 페이지의 Pageable을 계산하여 prevPageable에 저장합니다.
		for(int i = startPageNumber; i<this.currentPageNumber; i++) {
			startPageable = startPageable.previousOrFirst();
		}
		
		this.prevPageable = startPageable.getPageNumber() <= 0 ? null : startPageable.previousOrFirst();
		
		
		
		if(this.totalPageCount < endPageNumber){
			endPageNumber = this.totalPageCount;
			this.nextPageable = null;
		}
		
		  // 페이지 목록을 계산하여 pageableList에 저장합니다.
		for(int i = startPageNumber ; i <= endPageNumber; i++){
			pageableList.add(startPageable);
			startPageable = startPageable.next();
		}
		
		 // 다음 페이지의 Pageable을 계산하여 nextPageable에 저장합니다.
		this.nextPageable = startPageable.getPageNumber() + 1 < totalPageCount ? startPageable : null;
	    }
	
	
	public String makeQuery(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				//UriComponentsBuilder를 이용하여 새로운 URL 구성 요소 빌더 생성
		.queryParam("page", page)
		//URL에 `page`라는 쿼리파라미터 추가 
		//`page`라는 파라미터이름과 주어진 `page` 변수의 값이 쌍으로 추가됨 
		.build();
		//build() 빌더를 사용하여 URL구성 요소를 완성 

		return uriComponents.toUriString();
	}
	
	//주어진 페이지 번호를 기반으로 URL쿼리 파라미터를 생성하는 역할 
	//결과적으로 makeQuery메서드를 호출하면 주어진 페이지 번호를 포함한 
	//URI 쿼리 문자열이 생성되어 반환됨 
	//이 문자열은 페이지 이동에 사용됨 
	//예를 들어 makeQuery(3)을 호출하면 "page=3" 을 가지는 URL 문자열이 생성됨 
	
			
}
