package com.nature.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nature.domain.ChargeCoin;
import com.nature.domain.Member;
import com.nature.repository.ChargeCoinRepository;
import com.nature.repository.MemberRepository;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoinServiceImpl implements CoinService {

	private final ChargeCoinRepository chargeCoinRepository;
	
	private final MemberRepository memberRepository;
	
	
	@Transactional
	@Override
	public void grant(ChargeCoin chargeCoinEntity) throws Exception {
		Member memberEntity = memberRepository.findByNickname(chargeCoinEntity.getImagewriter());
	
		int coin = memberEntity.getCoin();
		int amount = chargeCoinEntity.getAmount();
		
		memberEntity.setCoin(coin + amount);
		memberRepository.save(memberEntity);
		chargeCoinRepository.save(chargeCoinEntity);
		
	}


	@Override
	public List<ChargeCoin> list() throws Exception {
		
		return chargeCoinRepository.findAll(Sort.by(Direction.DESC, "historyNo"));
	}


	@Override
	@Transactional(readOnly = true)
	public ChargeCoin readGranted(Long imageId) throws Exception {
		
		return chargeCoinRepository.findByImageId(imageId);
	}





}
