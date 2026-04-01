package com.kusoduck.stock.service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kusoduck.account.entity.Account;
import com.kusoduck.exception.DataNotExistException;
import com.kusoduck.exception.DateMismatchException;
import com.kusoduck.stock.service.eps.EpsService;
import com.kusoduck.stock.service.fcf.FreeCashFlowCrawlService;
import com.kusoduck.utils.DateConverter;
import com.kusoduck.utils.EmailUtils;
import com.kusoduck.utils.EncryptUtils;

@Service
public class CrawlerService {
	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	@Autowired
	private TradeDateService tradeDateService;
	@Autowired
	private StockDailyQuotesService stockDailyQuotesService;
	@Autowired
	private StockRatioService stockRatioService;
	@Autowired
	private InvestorsDailyTradingService investorsDailyTradingService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private EpsService epsService;
	@Autowired
	private DividendDataService dividendDataService;
	@Autowired
	private FreeCashFlowCrawlService freeCashFlowCrawlService;

	@Value("${crawl.today}")
	private boolean isFromToday;
	@Value("${crawl.date.from}")
	private String fromDate;
	@Value("${crawl.date.to}")
	private String toDate;

	@Value("${email.to}")
	private String emailTo;
	@Value("${email.from}")
	private String emailFrom;

	public void start() throws IOException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		try {
			if (isFromToday) {

				crawlData(LocalDate.now(), formatter);

			} else {
				// 1. 將字串轉換為 LocalDate 起始日期
				LocalDate startDate = LocalDate.parse(fromDate, formatter);
				LocalDate endDate = LocalDate.parse(toDate, formatter);

				// 2. 使用 loop 遍歷日期（直到今天為止）
				LocalDate current = startDate;

				// .isAfter(today) 判定是否超過今天
				while (!current.isAfter(endDate)) {

					crawlData(current, formatter);

					// 3. 日期遞增一天
					current = current.plusDays(1);

					// 4. 間隔延遲
					if (!current.isAfter(endDate)) { // 如果還有下一輪才睡，最後一天爬完不用睡
						try {
							Thread.sleep(10000);
						} catch (InterruptedException ex) {
							Thread.currentThread().interrupt();
						}
					}
				}
			}
		} catch (IOException | SQLException | DateMismatchException | DataNotExistException e) {
			logger.error(e.getMessage());
			/* Send Mail */
			Account account = accountService.findById("Gmail App").get();
			EmailUtils.sendFromGmail(account.getAccount(), EncryptUtils.decrypt(account.getPassword()), emailFrom,
					emailTo, "Stock crawler fail", e.getMessage());
		}

	}

	private void crawlData(LocalDate current, DateTimeFormatter formatter)
			throws IOException, SQLException, DateMismatchException, DataNotExistException {
		String currentString = current.format(formatter);

		stockDailyQuotesService.crawlData(currentString);
		stockRatioService.crawlData(currentString);
		dividendDataService.processDividendData(currentString);
		investorsDailyTradingService.crawlData(currentString);

		ckeckData(currentString);

		if (current.getDayOfMonth() == 1) {
			epsService.crawlData(current);
			freeCashFlowCrawlService.crawl(current);
		}
	}

	private void ckeckData(String dateString) throws SQLException, DateMismatchException, DataNotExistException {
		LocalDate intputDate = DateConverter.convert(dateString);
		LocalDate tradeDate = tradeDateService.findFirstByOrderByTradeDateDesc().get().getTradeDate();

		// 改成用intputDate去搜尋DB有沒有這筆資料
		if (!intputDate.isEqual(tradeDate)) {
			throw new DataNotExistException("Didn't get daily quotes data(" + dateString + ")");
		}

		if (!tradeDate.isEqual(stockRatioService.findFirstByOrderByTradeDateDesc().get().getId().getTradeDate())) {
			throw new DateMismatchException("stock ratio date not mapping trade_date");
		}

		if (!tradeDate
				.isEqual(investorsDailyTradingService.findFirstByOrderByTradeDateDesc().get().getId().getTradeDate())) {
			throw new DateMismatchException("investors trade date not mapping trade_date");
		}
	}
}
