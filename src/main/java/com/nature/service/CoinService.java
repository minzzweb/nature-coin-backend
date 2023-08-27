package com.nature.service;

import java.util.List;
import java.util.Optional;

import com.nature.domain.ChargeCoin;

public interface CoinService {

	public void grant(ChargeCoin chargeCoinEntity) throws Exception;

	public List<ChargeCoin> list() throws Exception;

	public ChargeCoin readGranted(Long imageId) throws Exception;

	
	
}