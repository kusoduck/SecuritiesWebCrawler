package com.kusoduck.stock.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.kusoduck.securities.entity.InvestorsDailyTrading;
import com.kusoduck.securities.html.parser.InvestorsDailyTradingParser;
import com.kusoduck.securities.repository.InvestorDailyTradingRepository;

@Service
public class InvestorsDailyTradingService {
	private static final Logger logger = LoggerFactory.getLogger(InvestorsDailyTradingService.class);

	private final InvestorDailyTradingRepository repository;

	@Autowired
	public InvestorsDailyTradingService(InvestorDailyTradingRepository theRepository) {
		repository = theRepository;
	}

	public Optional<InvestorsDailyTrading> findFirstByOrderByTradeDateDesc() {
		// 建議回傳 Optional，更安全地處理空值
		return repository.findFirstByOrderByIdTradeDateDesc();
	}

	@Transactional // 確保整個方法在一個事務中運行
	public void crawlData(String dateString) {
		List<InvestorsDailyTrading> investorsDailyTradings = InvestorsDailyTradingParser.parse(dateString);
		if (CollectionUtils.isEmpty(investorsDailyTradings)) {
			logger.info(String.format("Investors Daily Trading No data(%s)", dateString));
		} else {
			repository.saveAll(investorsDailyTradings);
		}
	}
}
