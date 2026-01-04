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

import com.kusoduck.securities.entity.StockDailyQuote;
import com.kusoduck.securities.entity.id.StockTradeId;
import com.kusoduck.stock.constant.StockDailyQuotesHeader;
import com.kusoduck.utils.DateConverter;
import com.kusoduck.utils.NumberHandleUtils;
import com.kusoduck.utils.ParseHtmlUtils;

public class StockDailyQuotesParser {
	private static Logger logger = LoggerFactory.getLogger(StockDailyQuotesParser.class);

	private static final String URL = "https://www.twse.com.tw/exchangeReport/MI_INDEX?response=html&type=ALLBUT0999&date=";

	private StockDailyQuotesParser() {

	}

	public static List<StockDailyQuote> parse(String dateString) {
		String url = URL + dateString;
		logger.info(String.format("%s Daily Quotes(All(no Warrant & CBBC & OCBBC))", dateString));
		logger.info(String.format("Parsing HTML: %s", url));

		try {
			/* Need jsoup.jar from http://jsoup.org/ */
			Document doc = ParseHtmlUtils.getDocument(url);

			Elements tableElements = doc.getElementsByTag("table");
			// 因為有多個表，所以要找出需要的表
			Element targetTableElement = findTargetTableElement(tableElements, "全部(不含權證、牛熊證)");

			if (targetTableElement != null) {
				Map<Integer, StockDailyQuotesHeader> colNumHeaderMap = getColumnNumberHeaderMap(targetTableElement);

				return getDailyQuotes(dateString, colNumHeaderMap, targetTableElement);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
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

	private static List<StockDailyQuote> getDailyQuotes(String dateString, Map<Integer, StockDailyQuotesHeader> colNumHeaderMap,
			Element targetTableElement) {
		List<StockDailyQuote> dailyQuotes = new ArrayList<>();
		for (Element tbodyElement : targetTableElement.getElementsByTag("tbody")) {
			for (Element trElement : tbodyElement.getElementsByTag("tr")) {
				StockDailyQuote entity = new StockDailyQuote();
				StockTradeId id = new StockTradeId();
				entity.setId(id);
				id.setTradeDate(DateConverter.convert(dateString));
				for (Element tdElement : trElement.getElementsByTag("td")) {
					StockDailyQuotesHeader header = colNumHeaderMap.get(tdElement.elementSiblingIndex());
					String value = tdElement.text();
					switch (header) {
					case CHANGE:
						entity.setPriceDiff(NumberHandleUtils.parseBigDecimal(value));
						break;
					case CLOSING_PRICE:
						entity.setClosingPrice(NumberHandleUtils.parseBigDecimal(value));
						break;
					case DIRECTION:
						entity.setDirection(value);
						break;
					case HIGHEST_PRICE:
						entity.setHighestPrice(NumberHandleUtils.parseBigDecimal(value));
						break;
					case LAST_BEST_ASK_PRICE:
						entity.setLastBestAskPrice(NumberHandleUtils.parseBigDecimal(value));
						break;
					case LAST_BEST_ASK_VOLUME:
						entity.setLastBestAskVolume(NumberHandleUtils.parseLong(value));
						break;
					case LAST_BEST_BID_PRICE:
						entity.setLastBestBidPrice(NumberHandleUtils.parseBigDecimal(value));
						break;
					case LAST_BEST_BID_VOLUME:
						entity.setLastBestBidVolume(NumberHandleUtils.parseLong(value));
						break;
					case LOWEST_PRICE:
						entity.setLowestPrice(NumberHandleUtils.parseBigDecimal(value));
						break;
					case OPENING_PRICE:
						entity.setOpeningPrice(NumberHandleUtils.parseBigDecimal(value));
						break;
					case PRICE_EARNING_RATIO:
						entity.setPriceEarningRatio(NumberHandleUtils.parseBigDecimal(value));
						break;
					case SECURITY_CODE:
						id.setStockCode(value);
						break;
					case SECURITY_NAME:
						entity.setSecurityName(value);
						break;
					case TRADE_VALUE:
						entity.setTradeValue(NumberHandleUtils.parseLong(value));
						break;
					case TRADE_VOLUME:
						entity.setTradeVolume(NumberHandleUtils.parseLong(value));
						break;
					case TRANSACTION:
						entity.setTransaction(NumberHandleUtils.parseLong(value));
						break;
					default:
						break;
					}
				}
				dailyQuotes.add(entity);
			}
		}
		return dailyQuotes;
	}

	private static Map<Integer, StockDailyQuotesHeader> getColumnNumberHeaderMap(Element targetTableElement) {
		Map<Integer, StockDailyQuotesHeader> colNumHeaderMap = new HashMap<>();
		for (Element theadElement : targetTableElement.getElementsByTag("thead")) {
			for (Element trElement : theadElement.getElementsByTag("tr")) {
				for (Element tdElement : trElement.getElementsByTag("th")) {
					StockDailyQuotesHeader dailyClostingMarketDataType = StockDailyQuotesHeader.getByZhTitle(tdElement.text());
					if (dailyClostingMarketDataType != null) {
						colNumHeaderMap.put(tdElement.elementSiblingIndex(), dailyClostingMarketDataType);
					}
				}
			}
		}
		return colNumHeaderMap;
	}
}