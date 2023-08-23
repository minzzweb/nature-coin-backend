package com.nature.service;

import org.springframework.stereotype.Service;

import com.nature.domain.Member;
import com.nature.domain.UserItem;
import com.nature.repository.MemberRepository;
import com.nature.repository.UserItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserItemServiceImpl implements UserItemService {

	private final UserItemRepository userItemRepository;
	
	
	
	private final MemberRepository memberRepository;
	
	@Transactional
	@Override
	public void register(Member member, Long itemId) {
		
		Long userNo = member.getUserNo();
		
		//Long itemId = item.getItemId(); 보류
		//int price = item.getPrice(); 보류
		
		UserItem userItem = new UserItem();
		userItem.setUserNo(userNo);
		userItem.setItemId(itemId);
		
		
		Member memberEntity = memberRepository.getOne(userNo);
		int coin = memberEntity.getCoin();
	
		
		//memberEntity.setCoin(coin - amount);
		
		memberRepository.save(memberEntity);
		userItemRepository.save(userItem);
		
	}

   //사용자 구매 상품 목록
	

}
