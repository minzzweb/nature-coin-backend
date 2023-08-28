package com.nature.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nature.domain.Image;
import com.nature.domain.Member;
import com.nature.domain.UserItem;
import com.nature.dto.Item;
import com.nature.dto.PageRequestVO;
import com.nature.repository.MemberRepository;
import com.nature.repository.UserItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserItemServiceImpl implements UserItemService {

	private final UserItemRepository userItemRepository;
	private final MemberRepository memberRepository;
		
	@Transactional
	@Override
	public void register(Member member, Item item) throws Exception {
		
		Long userNo = member.getUserNo();
		Long itemId = item.getItemId();
		
		log.info("item register :" +userNo + " , "+  itemId);
			
		UserItem userItem = new UserItem();
		userItem.setUserNo(userNo);
		userItem.setItemId(itemId);
		
		Member memberEntity = memberRepository.getOne(userNo);
		
		int coin = memberEntity.getCoin();
		int price = item.getPrice();
		
		memberEntity.setCoin(coin-price);
		
		memberRepository.save(memberEntity);
		userItemRepository.save(userItem);
	}

	
	@Override
	public Page<UserItem> list(Long userNo,PageRequestVO pageRequestVO) throws Exception {
		
		int pageNumber = pageRequestVO.getPage() - 1;
		int sizePerPage = pageRequestVO.getSizePerPage();
		
		Pageable pageRequest = PageRequest.of(pageNumber, sizePerPage, Sort.Direction.DESC, "userItemNo");
		log.info("List<UserItem> list(Long userNo) " + userNo);
		
		Page<UserItem> page = userItemRepository.findByUserNo(userNo,pageRequest);
		return page;
		
	}

}
