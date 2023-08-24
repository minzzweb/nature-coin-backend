package com.nature.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nature.domain.Member;
import com.nature.domain.UserItem;
import com.nature.dto.Item;
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
	public List<UserItem> list(Long userNo) throws Exception {
		
		log.info("List<UserItem> list(Long userNo) " + userNo);
		
		return userItemRepository.findByUserNo(userNo);
		
	}

}
