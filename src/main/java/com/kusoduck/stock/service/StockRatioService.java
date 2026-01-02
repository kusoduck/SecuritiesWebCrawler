package com.kusoduck.stock.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.kusoduck.securities.entity.StockRatio;
import com.kusoduck.securities.html.parser.StockRatiosParser;
import com.kusoduck.securities.repository.StockRatioRepository;

@Service
public class StockRatioService {
	private static final Logger logger = LoggerFactory.getLogger(StockRatioService.class);

	private final StockRatioRepository repository;

	@Autowired
	public StockRatioService(StockRatioRepository theRepository) {
		repository = theRepository;
	}

	public Optional<StockRatio> findFirstByOrderByTradeDateDesc() {
		// 建議回傳 Optional，更安全地處理空值
		return repository.findFirstByOrderByIdTradeDateDesc();
	}

	@Transactional // 確保整個方法在一個事務中運行
	public void crawlData(String dateString) {
		List<StockRatio> stockRatios = StockRatiosParser.parse(dateString);
		if (CollectionUtils.isEmpty(stockRatios)) {
			logger.info(String.format("Stock Ratio No data(%s)", dateString));
		} else {
			repository.saveAll(stockRatios);
		}
	}
}
