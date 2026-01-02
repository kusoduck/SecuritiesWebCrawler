package com.kusoduck.stock.service.fcf;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kusoduck.securities.entity.FreeCashFlowStatement;
import com.kusoduck.securities.html.MopsRedirector;
import com.kusoduck.securities.html.parser.FreeCashFlowStatementParser;
import com.kusoduck.securities.repository.FreeCashFlowStatementRepository;
import com.kusoduck.utils.CalcuateQuarterUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 使用 @RequiredArgsConstructor 這個 Lombok 註解，它會自動生成包含所有 final 欄位的建構函式
public class FreeCashFlowCrawlService {
	private static final Logger logger = LoggerFactory.getLogger(FreeCashFlowCrawlService.class);
	private static final String apiName = "ajax_t163sb20";

	private final MopsRedirector mopsRedirector;
	private final FreeCashFlowStatementParser freeCashFlowParser;
	private final FreeCashFlowStatementRepository repository;

	// @Autowired 如果類別中的欄位都是 final 且只有一個建構子,可以省略
//	public FreeCashFlowCrawlService(MopsRedirector mopsRedirector, FreeCashFlowStatementParser freeCashFlowParser,
//	        FreeCashFlowStatementRepository repository) {
//	    this.mopsRedirector = mopsRedirector;
//	    this.freeCashFlowParser = freeCashFlowParser;
//	    this.repository = repository;
//	}

	@Transactional
	public void crawl(LocalDate current) {
		Pair<Integer, Integer> previousRocYearQuarter = CalcuateQuarterUtils.calcPreviousYearQuarter(current);

		Integer year = previousRocYearQuarter.getLeft();
		Integer quarter = previousRocYearQuarter.getRight();
		Integer rocYear = CalcuateQuarterUtils.toRocYear(year);

		String url = mopsRedirector.getRedirectUrl(apiName, rocYear, quarter);
		logger.debug("Free Cash Flow Statement Parser - url : {}", url);

		List<FreeCashFlowStatement> freeCashFlowStatements = freeCashFlowParser.parse(url, year, quarter);

		repository.saveAll(freeCashFlowStatements);
	}
}
