package com.nature.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nature.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
