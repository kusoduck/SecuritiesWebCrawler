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

import stock.constant.RatiosOfSecuritiesColumn;

public class ParseHtmlStockRatiosStockExchange {
	private static final String URL = "https://www.twse.com.tw/exchangeReport/BWIBBU_d?response=html&selectType=ALL";

	public static List<Map<RatiosOfSecuritiesColumn, String>> praseHTMLTable(String date) {
		/* Need jsoup.jar from http://jsoup.org/ */
		String url = URL + "&date=" + date;
		System.out.println("日本益比、殖利率、股價淨值比(" + date + ")url:" + url);
		try {
			String[] ua = UserAgent.ua;
			Random random = new Random();
			Document doc = Jsoup.connect(url).timeout(10 * 1000).validateTLSCertificates(false)
					.userAgent(ua[random.nextInt(ua.length)]).maxBodySize(0).get();

			Elements tableElements = doc.getElementsByTag("table");
			Element targetTableElement = null;
			for (Element tableElement : tableElements) {
				targetTableElement = tableElement;
			}

			if (targetTableElement != null) {
				Map<Integer, RatiosOfSecuritiesColumn> titleOrderMap = new HashMap<>();
				setTitleOrderMap(titleOrderMap, targetTableElement);

				List<Map<RatiosOfSecuritiesColumn, String>> stockRatioDataList = new ArrayList<>();
				addStockData2List(stockRatioDataList, titleOrderMap, targetTableElement);

				return stockRatioDataList;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	private static void addStockData2List(List<Map<RatiosOfSecuritiesColumn, String>> stockRatioDataList,
			Map<Integer, RatiosOfSecuritiesColumn> titleOrderMap, Element targetTableElement) {

		for (Element tbodyElement : targetTableElement.getElementsByTag("tbody")) {
			for (Element trElement : tbodyElement.getElementsByTag("tr")) {
				Map<RatiosOfSecuritiesColumn, String> stockRatioDataMap = new EnumMap<>(RatiosOfSecuritiesColumn.class);
				for (Element tdElement : trElement.getElementsByTag("td")) {
					stockRatioDataMap.put(titleOrderMap.get(tdElement.elementSiblingIndex()), tdElement.text());
				}
				stockRatioDataList.add(stockRatioDataMap);
			}
		}
	}

	private static void setTitleOrderMap(Map<Integer, RatiosOfSecuritiesColumn> titleOrderMap,
			Element targetTableElement) {
		for (Element theadElement : targetTableElement.getElementsByTag("thead")) {
			for (Element trElement : theadElement.getElementsByTag("tr")) {
				for (Element tdElement : trElement.getElementsByTag("td")) {
					RatiosOfSecuritiesColumn ratiosOfSecuritiesColumn = RatiosOfSecuritiesColumn.get(tdElement.text());
					if (ratiosOfSecuritiesColumn != null) {
						titleOrderMap.put(tdElement.elementSiblingIndex(), ratiosOfSecuritiesColumn);
					}
				}
			}
		}
	}

}
