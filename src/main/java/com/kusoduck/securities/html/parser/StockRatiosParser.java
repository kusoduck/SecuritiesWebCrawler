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

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kusoduck.stock.constant.StockRatiosColumn;
import com.kusoduck.utils.ParseHtmlUtils;

public class StockRatiosParser {
	private static Logger logger = LoggerFactory.getLogger(StockRatiosParser.class);

	private static final String URL = "https://www.twse.com.tw/exchangeReport/BWIBBU_d?response=html&selectType=ALL&date=";

	private StockRatiosParser() {

	}

	public static List<Map<StockRatiosColumn, String>> parse(String date) {
		String url = URL + date;
		logger.info(String.format("%s Stock P/E ratio, dividend yield and P/B ratio", date));
		logger.info(String.format("Parsing HTML: %s", url));
		try {
			/* Need jsoup.jar from http://jsoup.org/ */
			Document doc = ParseHtmlUtils.getDocument(url);

			Elements tableElements = doc.getElementsByTag("table");
			if (tableElements.isEmpty()) {
				// skip if table is null
				return new ArrayList<>();
			}
			Element targetTableElement = tableElements.get(0);

			if (targetTableElement != null) {
				Map<Integer, StockRatiosColumn> orderColumnMap = new HashMap<>();
				setOrderColumnMap(orderColumnMap, targetTableElement);

				List<Map<StockRatiosColumn, String>> stockRatioDataList = new ArrayList<>();
				addStockData2List(stockRatioDataList, orderColumnMap, targetTableElement);
				return stockRatioDataList;
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	private static void addStockData2List(List<Map<StockRatiosColumn, String>> stockRatioDataList, Map<Integer, StockRatiosColumn> titleOrderMap,
			Element targetTableElement) {

		for (Element tbodyElement : targetTableElement.getElementsByTag("tbody")) {
			for (Element trElement : tbodyElement.getElementsByTag("tr")) {
				Map<StockRatiosColumn, String> stockRatioDataMap = new EnumMap<>(StockRatiosColumn.class);
				if (trElement.getElementsByTag("td").size() == 0) {
					System.out.println(trElement.text());
				}
				for (Element tdElement : trElement.getElementsByTag("td")) {
					StockRatiosColumn stockRatiosColumn = titleOrderMap.get(tdElement.elementSiblingIndex());
					if (stockRatiosColumn != null) {
						stockRatioDataMap.put(stockRatiosColumn, tdElement.text());
					}
				}
				stockRatioDataList.add(stockRatioDataMap);
			}
		}
	}

	private static void setOrderColumnMap(Map<Integer, StockRatiosColumn> titleOrderMap, Element targetTableElement) {
		for (Element theadElement : targetTableElement.getElementsByTag("thead")) {
			for (Element trElement : theadElement.getElementsByTag("tr")) {
				for (Element tdElement : trElement.getElementsByTag("th")) {
					StockRatiosColumn ratiosOfSecuritiesColumn = StockRatiosColumn.getByZhTitle(tdElement.text());
					if (ratiosOfSecuritiesColumn != null) {
						titleOrderMap.put(tdElement.elementSiblingIndex(), ratiosOfSecuritiesColumn);
					}
				}
			}
		}
	}

}
