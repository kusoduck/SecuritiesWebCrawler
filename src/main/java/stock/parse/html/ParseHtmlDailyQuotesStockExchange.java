/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.parse.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import stock.constant.DailyQuotesColumn;

public class ParseHtmlDailyQuotesStockExchange {
	private static final String URL = "https://www.twse.com.tw/exchangeReport/MI_INDEX?response=html&type=ALLBUT0999";

	public static List<Map<DailyQuotesColumn, String>> praseHTMLTable(String date) {
		/* Need jsoup.jar from http://jsoup.org/ */
		String url = URL + "&date=" + date;
		System.out.println("每日收盤行情(" + date + ")url:" + url);
		try {
			String[] ua = UserAgent.ua;
			Random random = new Random();
			Document doc = Jsoup.connect(url).timeout(10 * 1000).validateTLSCertificates(false)
					.userAgent(ua[random.nextInt(ua.length)]).maxBodySize(0).get();

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
				Map<Integer, DailyQuotesColumn> titleOrderMap = new HashMap<>();
				putTitleOrder2Map(titleOrderMap, targetTableElement);

				List<Map<DailyQuotesColumn, String>> stockTradeDataList = new ArrayList<>();
				addStockData2List(stockTradeDataList, titleOrderMap, targetTableElement);

				return stockTradeDataList;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	private static void addStockData2List(List<Map<DailyQuotesColumn, String>> stockTradeDataList,
			Map<Integer, DailyQuotesColumn> titleOrderMap, Element targetTableElement) {
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

	private static void putTitleOrder2Map(Map<Integer, DailyQuotesColumn> titleOrderMap, Element targetTableElement) {
		for (Element theadElement : targetTableElement.getElementsByTag("thead")) {
			for (Element trElement : theadElement.getElementsByTag("tr")) {
				for (Element tdElement : trElement.getElementsByTag("td")) {
					DailyQuotesColumn dailyClostingMarketDataType = DailyQuotesColumn.get(tdElement.text());
					if (dailyClostingMarketDataType != null) {
						titleOrderMap.put(tdElement.elementSiblingIndex(), dailyClostingMarketDataType);
					}
				}
			}

		}
	}
}