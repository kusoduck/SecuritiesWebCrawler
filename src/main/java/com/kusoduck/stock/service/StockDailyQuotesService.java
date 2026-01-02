package com.kusoduck.stock.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.kusoduck.securities.entity.StockDailyQuote;
import com.kusoduck.securities.html.parser.StockDailyQuotesParser;
import com.kusoduck.securities.repository.StockDailyQuoteRepository;

@Service
public class StockDailyQuotesService {
	private static final Logger logger = LoggerFactory.getLogger(StockDailyQuotesService.class);

	private final StockDailyQuoteRepository repository;

	@Autowired
	public StockDailyQuotesService(StockDailyQuoteRepository theRepository) {
		repository = theRepository;
	}

	@Transactional // 確保整個方法在一個事務中運行
	public void crawlData(String dateString) {
		List<StockDailyQuote> dailyQuotes = StockDailyQuotesParser.parse(dateString);
		if (CollectionUtils.isEmpty(dailyQuotes)) {
			logger.info(String.format("Stock Daily Quotes No data(%s)", dateString));
		} else {
			repository.saveAll(dailyQuotes);
		}
	}
}
