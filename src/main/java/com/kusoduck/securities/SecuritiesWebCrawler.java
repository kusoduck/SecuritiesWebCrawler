/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kusoduck.securities;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.kusoduck.securities.html.parser.IndexDailyQuotesParser;
import com.kusoduck.securities.html.parser.InvestorsDailyTradingParser;
import com.kusoduck.securities.html.parser.StockDailyQuotesParser;
import com.kusoduck.securities.html.parser.StockInfoParser;
import com.kusoduck.securities.html.parser.StockRatiosParser;
import com.kusoduck.stock.constant.IndexDailyQuotesColumn;
import com.kusoduck.stock.constant.InvestorsDailyTradingColumn;
import com.kusoduck.stock.constant.StockDailyQuotesColumn;
import com.kusoduck.stock.constant.StockRatiosColumn;
import com.kusoduck.stock.dao.IndexDailyQuotesDAO;
import com.kusoduck.stock.dao.InvestorsDailyTradingDAO;
import com.kusoduck.stock.dao.StockDailyQuotesDAO;
import com.kusoduck.stock.dao.StockInfoDAO;
import com.kusoduck.stock.dao.StockRatioDAO;
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

		} catch (SQLException | ParseException e) {
			logger.error(e.getMessage());
		}
	}

	private static void parseHtmlSecuritiesInfo(String date, Connection conn) {
		List<Map<IndexDailyQuotesColumn, String>> indiceDailyQuotes = IndexDailyQuotesParser.parse(date);
		if (CollectionUtils.isNotEmpty(indiceDailyQuotes)) {
			IndexDailyQuotesDAO.create(conn, date, indiceDailyQuotes);
		} else {
			logger.info(String.format("Indice Daily Quotes No data(%s)", date));
		}

		List<Map<StockDailyQuotesColumn, String>> stockDailyQuotes = StockDailyQuotesParser.parse(date);
		if (CollectionUtils.isNotEmpty(stockDailyQuotes)) {
			StockDailyQuotesDAO.create(conn, date, stockDailyQuotes);
		} else {
			logger.info(String.format("Stock Daily Quotes No data(%s)", date));
		}

		List<Map<StockRatiosColumn, String>> stockRatios = StockRatiosParser.parse(date);
		if (CollectionUtils.isNotEmpty(stockRatios)) {
			StockRatioDAO.create(conn, date, stockRatios);
		} else {
			logger.info(String.format("Stock Ratio No data(%s)", date));
		}

		List<Map<InvestorsDailyTradingColumn, String>> investorsDailyTradings = InvestorsDailyTradingParser.parse(date);
		if (CollectionUtils.isNotEmpty(investorsDailyTradings)) {
			InvestorsDailyTradingDAO.create(conn, date, investorsDailyTradings);
		} else {
			logger.info(String.format("Investors Daily Trading No data(%s)", date));
		}

	}
}
