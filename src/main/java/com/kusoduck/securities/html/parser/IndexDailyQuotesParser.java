/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kusoduck.securities.html.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kusoduck.securities.entity.IndiceDailyQuote;
import com.kusoduck.securities.entity.IndiceDailyQuoteId;
import com.kusoduck.stock.constant.IndexDailyQuotesHeader;
import com.kusoduck.utils.DateConverter;
import com.kusoduck.utils.NumberHandleUtils;
import com.kusoduck.utils.ParseHtmlUtils;

public class IndexDailyQuotesParser {
	private static Logger logger = LoggerFactory.getLogger(IndexDailyQuotesParser.class);

	private static final String URL = "https://www.twse.com.tw/exchangeReport/MI_INDEX?response=html&type=ALLBUT0999&date=";

	public static List<IndiceDailyQuote> parse(String date) {
		String url = URL + date;
		logger.info(String.format("%s Daily Quotes(All(no Warrant & CBBC & OCBBC))", date));
		logger.info(String.format("Parsing HTML: %s", url));

		try {
			/* Need jsoup.jar from http://jsoup.org/ */
			Document doc = ParseHtmlUtils.getDocument(url);

			Elements tableElements = doc.getElementsByTag("table");
			// 因為有多個表，所以要找出需要的表
			Element targetTableElement = findTargetTableElement(tableElements, "價格指數(臺灣證券交易所)");

			if (targetTableElement != null) {
				Map<Integer, IndexDailyQuotesHeader> colNumHeaderMap = getColumnNumberHeaderMap(targetTableElement);

				return getIndiceDailyQuotes(date, targetTableElement, colNumHeaderMap);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	private static List<IndiceDailyQuote> getIndiceDailyQuotes(String date, Element targetTableElement,	Map<Integer, IndexDailyQuotesHeader> colNumHeaderMap) {

		List<IndiceDailyQuote> indiceDailyQuotes = new ArrayList<>();
		for (Element tbodyElement : targetTableElement.getElementsByTag("tbody")) {
			for (Element trElement : tbodyElement.getElementsByTag("tr")) {
				int colNum = 0;
				IndiceDailyQuote entity = new IndiceDailyQuote();
				IndiceDailyQuoteId id = new IndiceDailyQuoteId();
				id.setDate(DateConverter.convert(date));
				for (Element tdElement : trElement.getElementsByTag("td")) {
					IndexDailyQuotesHeader header = colNumHeaderMap.get(colNum++);
					String value = tdElement.text();
					switch (header) {
					case CHANGE:
						entity.setIndexChange(NumberHandleUtils.parseBigDecimal(value));
						break;
					case CHANGE_PERCENT:
						entity.setChangePercent(NumberHandleUtils.parseBigDecimal(value));
						break;
					case CLOSING_INDEX:
						entity.setClosingIndex(NumberHandleUtils.parseBigDecimal(value));
						break;
					case DIRECTION:
						entity.setDirection(value);
						break;
					case INDEX_NAME:
						id.setIndexName(value);
						break;
					case NOTE:
						entity.setNote(value);
						break;
					default:
						break;
					}
				}
				entity.setId(id);
				indiceDailyQuotes.add(entity);
			}
		}
		return indiceDailyQuotes;
	}

	private static Element findTargetTableElement(Elements tableElements, String contain) {
		Element targetTableElement = null;
		for (Element tableElement : tableElements) {
			Elements thElements = tableElement.getElementsByTag("th");
			for (Element thElement : thElements) {
				if (thElement.text().contains(contain)) {
					targetTableElement = tableElement;
				}
			}
		}
		return targetTableElement;
	}

	private static Map<Integer, IndexDailyQuotesHeader> getColumnNumberHeaderMap(Element targetTableElement) {
		Map<Integer, IndexDailyQuotesHeader> colNumHeaderMap = new HashMap<>();
		for (Element theadElement : targetTableElement.getElementsByTag("thead")) {
			for (Element trElement : theadElement.getElementsByTag("tr")) {
				for (Element tdElement : trElement.getElementsByTag("th")) {
					IndexDailyQuotesHeader dailyClostingMarketDataType = IndexDailyQuotesHeader.getByZhTitle(tdElement.text());
					if (dailyClostingMarketDataType != null) {
						colNumHeaderMap.put(tdElement.elementSiblingIndex(), dailyClostingMarketDataType);
					}
				}
			}

		}
		return colNumHeaderMap;
	}
}