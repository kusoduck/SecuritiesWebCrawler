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

import com.kusoduck.securities.entity.StockRatio;
import com.kusoduck.securities.entity.StockRatioId;
import com.kusoduck.stock.constant.StockRatiosHeader;
import com.kusoduck.utils.DateConverter;
import com.kusoduck.utils.NumberHandleUtils;
import com.kusoduck.utils.ParseHtmlUtils;

public class StockRatiosParser {
	private static Logger logger = LoggerFactory.getLogger(StockRatiosParser.class);

	private static final String URL = "https://www.twse.com.tw/exchangeReport/BWIBBU_d?response=html&selectType=ALL&date=";

	public static List<StockRatio> parse(String dateString) {
		String url = URL + dateString;
		logger.info(String.format("%s Stock P/E ratio, dividend yield and P/B ratio", dateString));
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
				Map<Integer, StockRatiosHeader> colNumHeaderMap = getColumnNumberHeaderMap(targetTableElement);

				return getStockRatios(dateString, colNumHeaderMap, targetTableElement);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	private static List<StockRatio> getStockRatios(String dateString,Map<Integer, StockRatiosHeader> colNumHeaderMap, Element targetTableElement) {
		List<StockRatio> stockRatios = new ArrayList<>();
		for (Element tbodyElement : targetTableElement.getElementsByTag("tbody")) {
			for (Element trElement : tbodyElement.getElementsByTag("tr")) {
				if (trElement.getElementsByTag("td").size() == 0) {
					System.out.println(trElement.text());
				}

				StockRatio entity = new StockRatio();
				StockRatioId id = new StockRatioId();
				entity.setId(id);
				id.setTradeDate(DateConverter.convert(dateString));
				for (Element tdElement : trElement.getElementsByTag("td")) {
					StockRatiosHeader header = colNumHeaderMap.get(tdElement.elementSiblingIndex());
					String value = tdElement.text();
					if (header != null) {
						switch (header) {
						case DIVIDEND_YEAR:
							entity.setDividendYear(value);
							break;
						case DIVIDEND_YIELD:
							entity.setDividendYield(NumberHandleUtils.parseBigDecimal(value));
							break;
						case FINANCIAL_YEAR_QUARTER:
							entity.setFinancialYearQuarter(value);
							break;
						case PBR:
							entity.setPbr(NumberHandleUtils.parseBigDecimal(value));
							break;
						case PER:
							entity.setPer(NumberHandleUtils.parseBigDecimal(value));
							break;
						case SECURITY_CODE:
							id.setSecurityCode(value);
							break;
						case SECURITY_NAME:
							entity.setSecurityName(value);
							break;
						default:
							break;
						}
					}
				}
				stockRatios.add(entity);
			}
		}
		return stockRatios;
	}

	private static Map<Integer, StockRatiosHeader> getColumnNumberHeaderMap(Element targetTableElement) {
		Map<Integer, StockRatiosHeader> colNumHeaderMap = new HashMap<>();
		for (Element theadElement : targetTableElement.getElementsByTag("thead")) {
			for (Element trElement : theadElement.getElementsByTag("tr")) {
				for (Element tdElement : trElement.getElementsByTag("th")) {
					StockRatiosHeader ratiosOfSecuritiesColumn = StockRatiosHeader.getByZhTitle(tdElement.text());
					if (ratiosOfSecuritiesColumn != null) {
						colNumHeaderMap.put(tdElement.elementSiblingIndex(), ratiosOfSecuritiesColumn);
					}
				}
			}
		}
		return colNumHeaderMap;
	}

}
