package com.nature.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nature.domain.ChargeCoin;
import com.nature.domain.Member;
import com.nature.repository.ChargeCoinRepository;
import com.nature.repository.MemberRepository;

import jakarta.transaction.Transactional;
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


}
