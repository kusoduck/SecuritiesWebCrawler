/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kusoduck.securities.info.getter;

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

import com.kusoduck.securities.html.parser.DailyQuotesParser;
import com.kusoduck.securities.html.parser.InvestorsDailyTradingParser;
import com.kusoduck.securities.html.parser.StockInfoParser;
import com.kusoduck.securities.html.parser.StockRatiosParser;
import com.kusoduck.stock.constant.DailyQuotesColumn;
import com.kusoduck.stock.constant.InvestorsDailyTradingColumn;
import com.kusoduck.stock.constant.StockRatiosColumn;
import com.kusoduck.stock.dao.DailyQuotesDAO;
import com.kusoduck.stock.dao.InvestorsDailyTradingDAO;
import com.kusoduck.stock.dao.StockInfoDAO;
import com.kusoduck.stock.dao.StockRatioDAO;
import com.kusoduck.utils.MysqlConn;

public class GetStockData {
	private static Logger logger = Logger.getLogger(GetStockData.class);

	public static void main(String[] args) {
		/* change to vm argument -Dlog4j.configuration=file:__file_path__ */
		// PropertyConfigurator.configure("C:/Users/kusoduck/git/GetStockDataFromHtml/src/log4j.properties");
		String startDate = "";
		logger.info("(1.)匯入今日資料");
		logger.info("(2.)從指定日期開始");
		logger.info("(3.)從201801開始");
		String option;
		try (Scanner scanner = new Scanner(System.in)) {
			option = scanner.nextLine();

			if (option.compareToIgnoreCase("2") == 0) {
				logger.info("輸入年月日(YYYYMMDD)");
				startDate = scanner.nextLine();
			} else if (option.compareToIgnoreCase("3") == 0) {
				startDate = "20180101";
			}
		}

		MysqlConn connecter = new MysqlConn();
		try (Connection conn = connecter.getConn()) {

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
		List<Map<DailyQuotesColumn, String>> stockDailyQuotes = DailyQuotesParser.parse(date);
		if (CollectionUtils.isNotEmpty(stockDailyQuotes)) {
			DailyQuotesDAO.create(conn, date, stockDailyQuotes);
		} else {
			logger.info(String.format("Daily Quotes No data(%s)", date));
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
