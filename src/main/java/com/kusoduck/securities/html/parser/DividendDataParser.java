package com.kusoduck.securities.html.parser;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kusoduck.securities.entity.DividendData;
import com.kusoduck.utils.NumberHandleUtils;
import com.kusoduck.utils.ParseHtmlUtils;

public class DividendDataParser {
	private static Logger logger = LoggerFactory.getLogger(DividendDataParser.class);

//	private static String URL = "https://www.twse.com.tw/rwd/zh/exRight/TWT49U?startDate=20250828&endDate=20250904&response=html";
	private static final String BASE_URL = "https://www.twse.com.tw/rwd/zh/exRight/TWT49U?startDate=%s&endDate=%s&response=html";

	public static List<DividendData> parse(String startDate, String endDate) {
		String url = String.format(BASE_URL, startDate, endDate);
		logger.debug(String.format("URL: %s", url));

		List<DividendData> dividendDataPOs = new ArrayList<>();
		try {
			// Need jsoup.jar from http://jsoup.org/
			Document doc = ParseHtmlUtils.getDocument(url);

			Elements tableElements = doc.getElementsByTag("table");
			for (Element tableElement : tableElements) {
				Elements theadElements = tableElement.getElementsByTag("thead");
				Map<Integer, String> colNumHeaderMap = new HashMap<>();
				for (Element theadElement : theadElements) {
					Elements trElements = theadElement.getElementsByTag("tr");
					for (Element trElement : trElements) {
						Elements tdElements = trElement.getElementsByTag("td");
						int colNum = 0;
						for (Element tdElement : tdElements) {
							colNumHeaderMap.put(Integer.valueOf(colNum++), tdElement.text());
						}
					}
				}
				Elements tbodyElements = tableElement.getElementsByTag("tbody");
				for (Element tbodyElement : tbodyElements) {
					Elements trElements = tbodyElement.getElementsByTag("tr");
					for (Element trElement : trElements) {
						Elements tdElements = trElement.getElementsByTag("td");
						int colNum = 0;
						DividendData dividendDataPO =  new DividendData();
						for (Element tdElement : tdElements) {
							String header = colNumHeaderMap.get(colNum++);
							String value = tdElement.text();
							switch (header) {
							case "資料日期":
								dividendDataPO.setExDividendDate(convertROCDate(value));
								break;
							case "股票代號":
								dividendDataPO.setStockCode(value);
								break;
							case "除權息前收盤價":
								dividendDataPO.setClosingPriceBefore(NumberHandleUtils.parseBigDecimal(value));
								break;
							case "除權息參考價":
								dividendDataPO.setReferencePrice(NumberHandleUtils.parseBigDecimal(value));
								break;
							case "權值+息值":
								dividendDataPO.setDividendValue(NumberHandleUtils.parseBigDecimal(value));
								break;
							default:
								break;
							}
						}
						dividendDataPOs.add(dividendDataPO);
					}
				}
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return dividendDataPOs;
	}

	public static LocalDate convertROCDate(String rocDate) {
	    String[] parts = rocDate.replace("年", "-").replace("月", "-").replace("日", "").split("-");
	    int year = Integer.parseInt(parts[0]) + 1911; // 民國年轉換為西元年
	    int month = Integer.parseInt(parts[1]);
	    int day = Integer.parseInt(parts[2]);
	    return LocalDate.of(year, month, day);
	}
}
