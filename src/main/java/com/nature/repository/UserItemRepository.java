package com.nature.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nature.domain.UserItem;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {

	public Page<UserItem> findByUserNo(Long userNo ,Pageable pageRequest);
		
}
