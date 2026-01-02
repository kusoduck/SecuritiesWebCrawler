package com.kusoduck.stock.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kusoduck.securities.entity.TradeDate;
import com.kusoduck.securities.repository.TradeDateRepository;

@Service
public class TradeDateService {

	private final TradeDateRepository repository;

	@Autowired
	public TradeDateService(TradeDateRepository theRepository) {
		repository = theRepository;
	}

	public Optional<TradeDate> findFirstByOrderByTradeDateDesc() {
		// 建議回傳 Optional，更安全地處理空值
		return repository.findFirstByOrderByTradeDateDesc();
	}
}
