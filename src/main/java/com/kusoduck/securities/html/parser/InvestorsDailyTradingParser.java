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

import com.kusoduck.securities.entity.InvestorsDailyTrading;
import com.kusoduck.securities.entity.InvestorsDailyTradingId;
import com.kusoduck.stock.constant.InvestorsDailyTradingHeader;
import com.kusoduck.utils.DateConverter;
import com.kusoduck.utils.NumberHandleUtils;
import com.kusoduck.utils.ParseHtmlUtils;

public class InvestorsDailyTradingParser {
	private static Logger logger = LoggerFactory.getLogger(InvestorsDailyTradingParser.class);

	private static final String URL = "https://www.twse.com.tw/rwd/zh/fund/T86?date=#date#&selectType=ALLBUT0999&response=html";

	public static List<InvestorsDailyTrading> parse(String dateString) {
		String url = URL.replace("#date#", dateString);
		logger.info(String.format("%s Daily Trading Details of Foreign and Other Investors", dateString));
		logger.info(String.format("Parsing HTML: %s", url));
		try {
			// Need jsoup.jar from http://jsoup.org/
			Document doc = ParseHtmlUtils.getDocument(url);
			Elements tableElements = doc.getElementsByTag("table");
			if (tableElements.isEmpty()) {
				// skip if table is null
				return null;
			}
			Element targetTableElement = tableElements.get(0);

			if (targetTableElement != null) {
				Map<Integer, InvestorsDailyTradingHeader> colNumHeaderMap = getColumnNumberHeaderMap(targetTableElement);

				return getInvestorsDailyTradings(dateString, colNumHeaderMap, targetTableElement);
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	private static List<InvestorsDailyTrading> getInvestorsDailyTradings(String dateString, Map<Integer, InvestorsDailyTradingHeader> colNumHeaderMap,
			Element targetTableElement) {
		List<InvestorsDailyTrading> investorsDailyTradings = new ArrayList<>();
		for (Element tbodyElement : targetTableElement.getElementsByTag("tbody")) {
			for (Element trElement : tbodyElement.getElementsByTag("tr")) {
				InvestorsDailyTrading entity = new InvestorsDailyTrading();
				InvestorsDailyTradingId id = new InvestorsDailyTradingId();
				entity.setId(id);
				id.setTradeDate(DateConverter.convert(dateString));
				for (Element tdElement : trElement.getElementsByTag("td")) {
					InvestorsDailyTradingHeader header = colNumHeaderMap.get(tdElement.elementSiblingIndex());
					String value = tdElement.text();
					switch (header) {
					case DEALERS_DIFFERENCE:
						entity.setDealersDifference(NumberHandleUtils.parseBigDecimal(value));
						break;
					case DEALERS_HEDGE_DIFFERENCE:
						entity.setDealersHedgeDifference(NumberHandleUtils.parseBigDecimal(value));
						break;
					case DEALERS_HEDGE_TOTAL_BUY:
						entity.setDealersHedgeTotalBuy(NumberHandleUtils.parseBigDecimal(value));
						break;
					case DEALERS_HEDGE_TOTAL_SELL:
						entity.setDealersHedgeTotalSell(NumberHandleUtils.parseBigDecimal(value));
						break;
					case DEALERS_PROPRIETARY_DIFFERENCE:
						entity.setDealersProprietaryDifference(NumberHandleUtils.parseBigDecimal(value));
						break;
					case DEALERS_PROPRIETARY_TOTAL_BUY:
						entity.setDealersProprietaryTotalBuy(NumberHandleUtils.parseBigDecimal(value));
						break;
					case DEALERS_PROPRIETARY_TOTAL_SELL:
						entity.setDealersProprietaryTotalSell(NumberHandleUtils.parseBigDecimal(value));
						break;
					case FOREIGN_DEALERS_DIFFERENCE:
						entity.setForeignDealersDifference(NumberHandleUtils.parseBigDecimal(value));
						break;
					case FOREIGN_DEALERS_TOTAL_BUY:
						entity.setForeignDealersTotalBuy(NumberHandleUtils.parseBigDecimal(value));
						break;
					case FOREIGN_DEALERS_TOTAL_SELL:
						entity.setForeignDealersTotalSell(NumberHandleUtils.parseBigDecimal(value));
						break;
					case FOREIGN_INVESTORS_DIFFERENCE:
						entity.setForeignInvestorsDifference(NumberHandleUtils.parseBigDecimal(value));
						break;
					case FOREIGN_INVESTORS_TOTAL_BUY:
						entity.setForeignInvestorsTotalBuy(NumberHandleUtils.parseBigDecimal(value));
						break;
					case FOREIGN_INVESTORS_TOTAL_SELL:
						entity.setForeignInvestorsTotalSell(NumberHandleUtils.parseBigDecimal(value));
						break;
					case SECURITIES_INVESTMENT_TRUST_COMPANIES_TOTAL_BUY:
						entity.setSecuritiesInvestmentTrustCompaniesTotalBuy(NumberHandleUtils.parseBigDecimal(value));
						break;
					case SECURITIES_INVESTMENT_TRUST_COMPANIES_TOTAL_DIFFERENCE:
						entity.setSecuritiesInvestmentTrustCompaniesTotalDifference(NumberHandleUtils.parseBigDecimal(value));
						break;
					case SECURITIES_INVESTMENT_TRUST_COMPANIES_TOTAL_SELL:
						entity.setSecuritiesInvestmentTrustCompaniesTotalSell(NumberHandleUtils.parseBigDecimal(value));
						break;
					case SECURITY_CODE:
						id.setSecurityCode(value);
						break;
					case SECURITY_NAME:
						entity.setSecurityName(value);
						break;
					case TOTAL_DIFFERENCE:
						entity.setTotalDifference(NumberHandleUtils.parseBigDecimal(value));
						break;
					default:
						break;
					}
				}
				investorsDailyTradings.add(entity);
			}
		}
		return investorsDailyTradings;
	}

	private static Map<Integer, InvestorsDailyTradingHeader> getColumnNumberHeaderMap(Element targetTableElement) {
		Map<Integer, InvestorsDailyTradingHeader> colNumHeaderMap = new HashMap<>();
		for (Element theadElement : targetTableElement.getElementsByTag("thead")) {
			for (Element trElement : theadElement.getElementsByTag("tr")) {
				for (Element tdElement : trElement.getElementsByTag("th")) {
					InvestorsDailyTradingHeader header = InvestorsDailyTradingHeader.getByZhTitle(tdElement.text());
					if (header != null) {
						colNumHeaderMap.put(tdElement.elementSiblingIndex(), header);
					}
				}
			}
		}
		return colNumHeaderMap;
	}

}
