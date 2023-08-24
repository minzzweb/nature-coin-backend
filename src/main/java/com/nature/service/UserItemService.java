package com.nature.service;

import java.util.List;

import com.nature.domain.Member;
import com.nature.domain.UserItem;
import com.nature.dto.Item;

public interface UserItemService {

    public void register(Member member, Item item) throws Exception;

    public List<UserItem> list(Long userNo) throws Exception;

	}
