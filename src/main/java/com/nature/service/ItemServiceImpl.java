package com.nature.service;

import org.springframework.stereotype.Service;

import com.nature.domain.Item;
import com.nature.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

	private final ItemRepository repository;

}
