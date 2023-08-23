package com.nature.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nature.domain.ChargeCoin;

public interface ChargeCoinRepository extends JpaRepository<ChargeCoin, Long> {

}