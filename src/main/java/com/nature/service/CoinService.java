package com.nature.service;

import java.util.List;

import com.nature.domain.ChargeCoin;

public interface CoinService {

	public void grant(ChargeCoin chargeCoinEntity) throws Exception;
	
}