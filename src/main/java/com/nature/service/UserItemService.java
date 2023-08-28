package com.nature.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nature.domain.Member;
import com.nature.domain.UserItem;
import com.nature.dto.Item;
import com.nature.dto.PageRequestVO;

public interface UserItemService {

    public void register(Member member, Item item) throws Exception;

    public Page<UserItem> list(Long userNo,PageRequestVO pageRequestVO) throws Exception;

	}
