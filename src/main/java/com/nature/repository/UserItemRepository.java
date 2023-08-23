package com.nature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nature.domain.UserItem;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {

public List<UserItem> findByUserNo(Long userNo);

		// 사용자 구매 상품 목록
		@Query("SELECT a.userItemNo, a.userNo, a.itemId, a.regDate "
				+ "FROM UserItem a "
				+ "WHERE a.userNo = ?1 "
				+ "ORDER BY a.userItemNo DESC, a.regDate DESC")
		public List<Object[]> listUserItem(Long userNo);
		
		// 구매 상품 보기
		List<Object[]> findByUserItemNo(Long userItemNo);
		
}
