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

import com.kusoduck.stock.constant.StockDailyQuotesColumn;
import com.kusoduck.utils.ParseHtmlUtils;

public class StockDailyQuotesParser {
	private static Logger logger = Logger.getLogger(StockDailyQuotesParser.class);
	private static final String URL = "https://www.twse.com.tw/exchangeReport/MI_INDEX?response=html&type=ALLBUT0999&date=";

	private StockDailyQuotesParser() {

	}

	public static List<Map<StockDailyQuotesColumn, String>> parse(String date) {
		String url = URL + date;
		logger.info(String.format("%s Daily Quotes(All(no Warrant & CBBC & OCBBC))", date));
		logger.info(String.format("Parsing HTML: %s", url));
		try {
			/* Need jsoup.jar from http://jsoup.org/ */
			Document doc = ParseHtmlUtils.getDocument(url);

			Elements tableElements = doc.getElementsByTag("table");
			Element targetTableElement = null;
			for (Element tableElement : tableElements) {
				Elements thElements = tableElement.getElementsByTag("th");
				for (Element thElement : thElements) {
					if (thElement.text().contains("全部(不含權證、牛熊證)")) {
						targetTableElement = tableElement;
					}
				}
			}

			if (targetTableElement != null) {
				Map<Integer, StockDailyQuotesColumn> orderColumnMap = new HashMap<>();
				setOrderColumnMap(orderColumnMap, targetTableElement);

				List<Map<StockDailyQuotesColumn, String>> stockTradeDataList = new ArrayList<>();
				addStockData2List(stockTradeDataList, orderColumnMap, targetTableElement);
				return stockTradeDataList;
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	private static void addStockData2List(List<Map<StockDailyQuotesColumn, String>> stockTradeDataList, Map<Integer, StockDailyQuotesColumn> titleOrderMap,
			Element targetTableElement) {
		for (Element tbodyElement : targetTableElement.getElementsByTag("tbody")) {
			for (Element trElement : tbodyElement.getElementsByTag("tr")) {
				Map<StockDailyQuotesColumn, String> stockTradeDataMap = new EnumMap<>(StockDailyQuotesColumn.class);
				for (Element tdElement : trElement.getElementsByTag("td")) {
					stockTradeDataMap.put(titleOrderMap.get(tdElement.elementSiblingIndex()), tdElement.text());
				}
				stockTradeDataList.add(stockTradeDataMap);
			}
		}

	}

	private static void setOrderColumnMap(Map<Integer, StockDailyQuotesColumn> titleOrderMap, Element targetTableElement) {
		for (Element theadElement : targetTableElement.getElementsByTag("thead")) {
			for (Element trElement : theadElement.getElementsByTag("tr")) {
				for (Element tdElement : trElement.getElementsByTag("td")) {
					StockDailyQuotesColumn dailyClostingMarketDataType = StockDailyQuotesColumn.getByZhTitle(tdElement.text());
					if (dailyClostingMarketDataType != null) {
						titleOrderMap.put(tdElement.elementSiblingIndex(), dailyClostingMarketDataType);
					}
				}
			}

		}
	}
}