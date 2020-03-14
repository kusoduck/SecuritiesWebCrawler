/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parse.html.securities;

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

import stock.constant.StockRatiosTitle;
import utils.parse.html.ParseHtmlUtils;

public class StockRatiosParser {
	private static Logger logger = Logger.getLogger(StockRatiosParser.class);
	private static final String URL = "https://www.twse.com.tw/exchangeReport/BWIBBU_d?response=html&selectType=ALL&date=";

	private StockRatiosParser() {

	}

	public static List<Map<StockRatiosTitle, String>> parse(String date) {
		List<Map<StockRatiosTitle, String>> stockRatioDataList = new ArrayList<>();

		String url = URL + date;
		logger.debug(String.format("%s Daily Quotes(All(no Warrant & CBBC & OCBBC))", date));
		logger.debug(String.format("URL: %s", url));
		try {
			/* Need jsoup.jar from http://jsoup.org/ */
			Document doc = ParseHtmlUtils.getDocument(url);

			Elements tableElements = doc.getElementsByTag("table");
			Element targetTableElement = null;
			for (Element tableElement : tableElements) {
				targetTableElement = tableElement;
			}

			if (targetTableElement != null) {
				Map<Integer, StockRatiosTitle> titleOrderMap = new HashMap<>();
				setTitleOrderMap(titleOrderMap, targetTableElement);

				addStockData2List(stockRatioDataList, titleOrderMap, targetTableElement);

				return stockRatioDataList;
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return stockRatioDataList;
	}

	private static void addStockData2List(List<Map<StockRatiosTitle, String>> stockRatioDataList,
			Map<Integer, StockRatiosTitle> titleOrderMap, Element targetTableElement) {

		for (Element tbodyElement : targetTableElement.getElementsByTag("tbody")) {
			for (Element trElement : tbodyElement.getElementsByTag("tr")) {
				Map<StockRatiosTitle, String> stockRatioDataMap = new EnumMap<>(StockRatiosTitle.class);
				for (Element tdElement : trElement.getElementsByTag("td")) {
					stockRatioDataMap.put(titleOrderMap.get(tdElement.elementSiblingIndex()), tdElement.text());
				}
				stockRatioDataList.add(stockRatioDataMap);
			}
		}
	}

	private static void setTitleOrderMap(Map<Integer, StockRatiosTitle> titleOrderMap,
			Element targetTableElement) {
		for (Element theadElement : targetTableElement.getElementsByTag("thead")) {
			for (Element trElement : theadElement.getElementsByTag("tr")) {
				for (Element tdElement : trElement.getElementsByTag("td")) {
					StockRatiosTitle ratiosOfSecuritiesColumn = StockRatiosTitle.get(tdElement.text());
					if (ratiosOfSecuritiesColumn != null) {
						titleOrderMap.put(tdElement.elementSiblingIndex(), ratiosOfSecuritiesColumn);
					}
				}
			}
		}
	}

}
