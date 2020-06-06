/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kusoduck.securities.html.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.kusoduck.stock.constant.InvestorsDailyTradingColumn;
import com.kusoduck.utils.ParseHtmlUtils;

public class InvestorsDailyTradingParser {
	private static Logger logger = Logger.getLogger(InvestorsDailyTradingParser.class);
	private static final String URL = "http://www.twse.com.tw/fund/T86?response=html&selectType=ALLBUT0999&date=";

	private InvestorsDailyTradingParser() {

	}

	public static List<Map<InvestorsDailyTradingColumn, String>> parse(String date) {
		String url = URL + date;
		logger.debug(String.format("%s Daily Trading Details of Foreign and Other Investors", date));
		logger.debug(String.format("URL: %s", url));
		try {
			// Need jsoup.jar from http://jsoup.org/
			Document doc = ParseHtmlUtils.getDocument(url);
			Elements tableElements = doc.getElementsByTag("table");
			if (tableElements.isEmpty()) {
				// skip if table is null
				return new ArrayList<>();
			}
			Element targetTableElement = tableElements.get(0);

			if (targetTableElement != null) {
				Map<Integer, InvestorsDailyTradingColumn> orderColumnMap = new HashMap<>();
				setOrderColumnMap(orderColumnMap, targetTableElement);

				List<Map<InvestorsDailyTradingColumn, String>> investorsDailyTradings = new ArrayList<>();
				addStockData2List(investorsDailyTradings, orderColumnMap, targetTableElement);
				return investorsDailyTradings;
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	private static void addStockData2List(List<Map<InvestorsDailyTradingColumn, String>> investorsDailyTradings,
			Map<Integer, InvestorsDailyTradingColumn> orderColumnMap, Element targetTableElement) {
		for (Element tbodyElement : targetTableElement.getElementsByTag("tbody")) {
			for (Element trElement : tbodyElement.getElementsByTag("tr")) {
				Map<InvestorsDailyTradingColumn, String> investorsDailyTradingDataMap = new EnumMap<>(InvestorsDailyTradingColumn.class);
				for (Element tdElement : trElement.getElementsByTag("td")) {
					investorsDailyTradingDataMap.put(orderColumnMap.get(tdElement.elementSiblingIndex()), tdElement.text());
				}
				investorsDailyTradings.add(investorsDailyTradingDataMap);
			}
		}
	}

	private static void setOrderColumnMap(Map<Integer, InvestorsDailyTradingColumn> orderColumnMap, Element targetTableElement) {
		for (Element theadElement : targetTableElement.getElementsByTag("thead")) {
			for (Element trElement : theadElement.getElementsByTag("tr")) {
				for (Element tdElement : trElement.getElementsByTag("td")) {
					InvestorsDailyTradingColumn investorsDailyTradingColumn = InvestorsDailyTradingColumn.getByZhTitle(tdElement.text());
					if (investorsDailyTradingColumn != null) {
						orderColumnMap.put(tdElement.elementSiblingIndex(), investorsDailyTradingColumn);
					}
				}
			}
		}
	}

}
