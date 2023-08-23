package com.nature.service;

import com.nature.domain.Member;
import com.nature.dto.Item;

public interface UserItemService {

    public void register(Member member, Item item) throws Exception;

	}
