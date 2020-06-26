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

import com.kusoduck.stock.constant.DailyQuotesColumn;
import com.kusoduck.utils.ParseHtmlUtils;

public class DailyQuotesParser {
	private static Logger logger = Logger.getLogger(DailyQuotesParser.class);
	private static final String URL = "https://www.twse.com.tw/exchangeReport/MI_INDEX?response=html&type=ALLBUT0999&date=";

	private DailyQuotesParser() {

	}

	public static List<Map<DailyQuotesColumn, String>> parse(String date) {
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
				Map<Integer, DailyQuotesColumn> orderColumnMap = new HashMap<>();
				setOrderColumnMap(orderColumnMap, targetTableElement);

				List<Map<DailyQuotesColumn, String>> stockTradeDataList = new ArrayList<>();
				addStockData2List(stockTradeDataList, orderColumnMap, targetTableElement);
				return stockTradeDataList;
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	private static void addStockData2List(List<Map<DailyQuotesColumn, String>> stockTradeDataList, Map<Integer, DailyQuotesColumn> titleOrderMap,
			Element targetTableElement) {
		for (Element tbodyElement : targetTableElement.getElementsByTag("tbody")) {
			for (Element trElement : tbodyElement.getElementsByTag("tr")) {
				Map<DailyQuotesColumn, String> stockTradeDataMap = new EnumMap<>(DailyQuotesColumn.class);
				for (Element tdElement : trElement.getElementsByTag("td")) {
					stockTradeDataMap.put(titleOrderMap.get(tdElement.elementSiblingIndex()), tdElement.text());
				}
				stockTradeDataList.add(stockTradeDataMap);
			}
		}

	}

	private static void setOrderColumnMap(Map<Integer, DailyQuotesColumn> titleOrderMap, Element targetTableElement) {
		for (Element theadElement : targetTableElement.getElementsByTag("thead")) {
			for (Element trElement : theadElement.getElementsByTag("tr")) {
				for (Element tdElement : trElement.getElementsByTag("td")) {
					DailyQuotesColumn dailyClostingMarketDataType = DailyQuotesColumn.getByZhTitle(tdElement.text());
					if (dailyClostingMarketDataType != null) {
						titleOrderMap.put(tdElement.elementSiblingIndex(), dailyClostingMarketDataType);
					}
				}
			}

		}
	}
}