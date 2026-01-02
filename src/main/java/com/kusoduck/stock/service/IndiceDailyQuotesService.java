package com.kusoduck.stock.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kusoduck.securities.entity.IndiceDailyQuote;
import com.kusoduck.securities.html.parser.IndexDailyQuotesParser;
import com.kusoduck.securities.repository.IndiceDailyQuotesRepository;

@Service
public class IndiceDailyQuotesService {
	private static final Logger logger = LoggerFactory.getLogger(IndiceDailyQuotesService.class);

	private final IndiceDailyQuotesRepository repository;

	@Autowired
	public IndiceDailyQuotesService(IndiceDailyQuotesRepository quotesRepository) {
		repository = quotesRepository;
	}

	@Transactional // 確保整個方法在一個事務中運行
	public void crawlData(String dateString) throws IOException {
		logger.debug("Parsing indice daily quotes data...");
		List<IndiceDailyQuote> indiceDailyQuotes = IndexDailyQuotesParser.parse(dateString);

		if (CollectionUtils.isEmpty(indiceDailyQuotes)) {
			logger.info(String.format("Indice Daily Quotes No data(%s)", dateString));
		} else {
			logger.debug("Saving " + indiceDailyQuotes.size() + " records to the database...");
			repository.saveAll(indiceDailyQuotes);
			logger.debug("Save completed.");
		}
	}
}
