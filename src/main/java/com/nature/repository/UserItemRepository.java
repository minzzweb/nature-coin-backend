package com.nature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nature.domain.UserItem;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {

	public List<UserItem> findByUserNo(Long userNo);
		
}
