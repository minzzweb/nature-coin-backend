package com.nature.service;

import org.springframework.stereotype.Service;

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

	
	
	@Override
	public void grant(ChargeCoin chargeCoinEntity) throws Exception {
		Member memberEntity = memberRepository.findByNickname(chargeCoinEntity.getImagewriter());
	
		int coin = memberEntity.getCoin();
		int amount = chargeCoinEntity.getAmount();
		
		memberEntity.setCoin(coin + amount);
		memberRepository.save(memberEntity);
		chargeCoinRepository.save(chargeCoinEntity);
		
	}

    

}
