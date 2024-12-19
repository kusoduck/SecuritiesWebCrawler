/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kusoduck.stock.app;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.kusoduck.exception.DateMismatchException;
import com.kusoduck.securities.html.parser.IndexDailyQuotesParser;
import com.kusoduck.securities.html.parser.InvestorsDailyTradingParser;
import com.kusoduck.securities.html.parser.StockDailyQuotesParser;
import com.kusoduck.securities.html.parser.StockInfoParser;
import com.kusoduck.securities.html.parser.StockRatiosParser;
import com.kusoduck.stock.constant.IndexDailyQuotesColumn;
import com.kusoduck.stock.constant.InvestorsDailyTradingColumn;
import com.kusoduck.stock.constant.StockDailyQuotesColumn;
import com.kusoduck.stock.constant.StockRatiosColumn;
import com.kusoduck.stock.crawler.EpsCrawler;
import com.kusoduck.stock.dao.EpsDAO;
import com.kusoduck.stock.dao.IndexDailyQuotesDAO;
import com.kusoduck.stock.dao.InvestorsDailyTradingDAO;
import com.kusoduck.stock.dao.StockDailyQuotesDAO;
import com.kusoduck.stock.dao.StockInfoDAO;
import com.kusoduck.stock.dao.StockQuarterlyEpsDAO;
import com.kusoduck.stock.dao.StockRatioDAO;
import com.kusoduck.stock.po.EpsPO;
import com.kusoduck.stock.po.StockQuarterlyEpsPO;
import com.kusoduck.utils.CalcuateQuarterUtils;
import com.kusoduck.utils.MySQLConnector;

public class SecuritiesWebCrawler {
	private static Logger logger = Logger.getLogger(SecuritiesWebCrawler.class);

	public static void main(String[] args) {
		String log4jPath = System.getProperty("log4j.config", "src/log4j.properties");
		PropertyConfigurator.configure(log4jPath);

		boolean isAuto = Boolean.parseBoolean(System.getProperty("auto", "true"));

		String startDate = "";
		logger.info("(1.)匯入今日資料");
		logger.info("(2.)從指定日期開始");
		logger.info("(3.)從201801開始");
		String option;
		if (isAuto) {
			option = "1";
		} else {
			try (Scanner scanner = new Scanner(System.in)) {
				option = scanner.nextLine();

				if (option.compareToIgnoreCase("2") == 0) {
					logger.info("輸入年月日(YYYYMMDD)");
					startDate = scanner.nextLine();
				} else if (option.compareToIgnoreCase("3") == 0) {
					startDate = "20180101";
				}
			}
		}

		try (Connection conn = MySQLConnector.getConn("securities")) {

			if (CollectionUtils.isEmpty(StockInfoDAO.select(conn))) {
				StockInfoParser.parse(conn);
			}
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
			switch (option) {
			case "1":
				parseHtmlSecuritiesInfo(formatDate.format(new Date()), conn);
				break;
			case "2":
			case "3":
				Date date1 = formatDate.parse(startDate);
				Date date2 = new Date();
				long diff = date2.getTime() - date1.getTime();
				long days = diff / (1000 * 60 * 60 * 24);

				Calendar calen = Calendar.getInstance();
				calen.setTime(date1);
				if (days != 0) {
					for (int i = 0; i <= days; i++) {
						date1 = calen.getTime();
						parseHtmlSecuritiesInfo(formatDate.format(date1), conn);
						calen.add(Calendar.DATE, 1);

						try {
							Thread.sleep(10000);
						} catch (InterruptedException ex) {
						}
					}
				} else {
					parseHtmlSecuritiesInfo(startDate, conn);
				}
				break;
			default:
				break;
			}
			logger.info("End");

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private static void parseHtmlSecuritiesInfo(String dateString, Connection conn)
			throws SQLException, DateMismatchException {
		List<Map<IndexDailyQuotesColumn, String>> indiceDailyQuotes = IndexDailyQuotesParser.parse(dateString);
		if (CollectionUtils.isNotEmpty(indiceDailyQuotes)) {
			IndexDailyQuotesDAO.create(conn, dateString, indiceDailyQuotes);
		} else {
			logger.info(String.format("Indice Daily Quotes No data(%s)", dateString));
		}

		List<Map<StockDailyQuotesColumn, String>> stockDailyQuotes = StockDailyQuotesParser.parse(dateString);
		if (CollectionUtils.isNotEmpty(stockDailyQuotes)) {
			StockDailyQuotesDAO.create(conn, dateString, stockDailyQuotes);
		} else {
			logger.info(String.format("Stock Daily Quotes No data(%s)", dateString));
		}

		List<Map<StockRatiosColumn, String>> stockRatios = StockRatiosParser.parse(dateString);
		if (CollectionUtils.isNotEmpty(stockRatios)) {
			StockRatioDAO.create(conn, dateString, stockRatios);
		} else {
			logger.info(String.format("Stock Ratio No data(%s)", dateString));
		}

		List<Map<InvestorsDailyTradingColumn, String>> investorsDailyTradings = InvestorsDailyTradingParser
				.parse(dateString);
		if (CollectionUtils.isNotEmpty(investorsDailyTradings)) {
			InvestorsDailyTradingDAO.create(conn, dateString, investorsDailyTradings);
		} else {
			logger.info(String.format("Investors Daily Trading No data(%s)", dateString));
		}

		ckeckData(conn, dateString);

		handelEpsCrawler(dateString, conn);
	}

	private static void ckeckData(Connection conn, String dateString) throws SQLException, DateMismatchException {
		String date = "";
		String sql = "select * from v_trade_date order by trade_date desc limit 0,1";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					date = rs.getString("trade_date");
				}
			}
		}

		sql = "select distinct trade_date from securities.t_stock_ratio order by TRADE_DATE desc limit 0,1";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next() && !date.equals(rs.getString("trade_date"))) {
					throw new DateMismatchException("stock ratio date not mapping trade_date");
				}
			}
		}

		sql = "select distinct trade_date from securities.t_investors_daily_trading order by TRADE_DATE desc limit 0,1";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next() && !date.equals(rs.getString("trade_date"))) {
					throw new DateMismatchException("investors trade date not mapping trade_date");
				}
			}
		}
	}

	private static void handelEpsCrawler(String dateString, Connection conn) throws SQLException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		Pair<Integer, Integer> currentYearQuarter = CalcuateQuarterUtils.getYearAndQuarter(localDate);
		Pair<Integer, Integer> previousYearQuarter = CalcuateQuarterUtils
				.calcPreviousQuarter(currentYearQuarter.getLeft(), currentYearQuarter.getRight());
		logger.debug(previousYearQuarter.getLeft() + "-" + previousYearQuarter.getRight());

		int year = previousYearQuarter.getLeft();
		int quarter = previousYearQuarter.getRight();
		int rocYear = year - 1911;
		List<EpsPO> epsPOs = EpsCrawler.crawl(rocYear, quarter);
		List<EpsPO> dbEpsPO = EpsDAO.find(conn, year, quarter);
		epsPOs = (List<EpsPO>) CollectionUtils.removeAll(epsPOs, dbEpsPO);
		EpsDAO.insert(conn, epsPOs);

		// 計算出每季的EPS
		if (quarter > 1) {
			for (EpsPO epsPO : epsPOs) {
				// 取得上一季的前一季，用來計算季與季的差額
				Pair<Integer, Integer> lastYearQuarterPair = CalcuateQuarterUtils.calcPreviousQuarter(year,
						quarter);

				String stockSymbol = epsPO.getSecurityCode();
				EpsPO lastEpsPO = EpsDAO.find(conn, lastYearQuarterPair.getLeft(), lastYearQuarterPair.getRight(),
						stockSymbol);
				if (lastEpsPO != null) {
					BigDecimal quarterlyEps = epsPO.getEps().subtract(lastEpsPO.getEps());
					StockQuarterlyEpsPO stockQuarterlyEpsPO = new StockQuarterlyEpsPO(stockSymbol, year, quarter,
							quarterlyEps);
					StockQuarterlyEpsDAO.create(conn, stockQuarterlyEpsPO);
				}
			}
		}
		conn.commit();
	}

}
