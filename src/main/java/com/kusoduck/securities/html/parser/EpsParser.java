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
import org.springframework.stereotype.Service;

import com.kusoduck.securities.entity.CumulativeEpsData;
import com.kusoduck.securities.entity.id.StockReportId;
import com.kusoduck.utils.NumberHandleUtils;
import com.kusoduck.utils.ParseHtmlUtils;

@Service
public class EpsParser {
	private static Logger logger = LoggerFactory.getLogger(EpsParser.class);

	public List<CumulativeEpsData> parse(String url, int rocYear, int quarter) {
		List<CumulativeEpsData> totalCumulativeEpsDataList = new ArrayList<>();
		try {
			Document doc = ParseHtmlUtils.getDocument(url);
			Elements tables = doc.select("table.hasBorder");
			for (Element table : tables) {
				Map<Integer, String> colNumHeaderMap = getColumnNumberHeaderMap(table);
				Elements trElements = table.select("tr.odd");
				if (trElements.isEmpty()) {
					trElements = table.select("tr.even");
				}
				totalCumulativeEpsDataList.addAll(getCumulativeEpsList(colNumHeaderMap, trElements, rocYear, quarter));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return totalCumulativeEpsDataList;
	}

	private List<CumulativeEpsData> getCumulativeEpsList(Map<Integer, String> columnIndexTitleMap, Elements trElements, int year, int reportQuarter) {
		List<CumulativeEpsData> cumulativeEpsList = new ArrayList<>();
		for (Element trElement : trElements) {
			Elements tdElements = trElement.select("td");
			int i = 0;
			CumulativeEpsData entity = new CumulativeEpsData();
			StockReportId id = new StockReportId();
			entity.setId(id);
			id.setReportYear(Integer.valueOf(year + 1911).shortValue());
			id.setReportQuarter(Integer.valueOf(reportQuarter).byteValue());
			for (Element tdElement : tdElements) {
				String value = tdElement.text();
				switch (columnIndexTitleMap.get(i++)) {
				case "公司 代號":
					id.setStockCode(value);
					break;
				case "基本每股盈餘（元）":
					entity.setEps(NumberHandleUtils.parseBigDecimal(value));
					break;
				default:
					break;
				}
			}
			cumulativeEpsList.add(entity);
		}
		return cumulativeEpsList;
	}

	private Map<Integer, String> getColumnNumberHeaderMap(Element tableElement) {
		Map<Integer, String> colNumHeaderMap = new HashMap<>();
		Elements headRow = tableElement.select("tr.tblHead");
		Elements thElements = headRow.get(0).select("th");
		int i = 0;
		for (Element thElement : thElements) {
			colNumHeaderMap.put(i++, thElement.text());
		}
		return colNumHeaderMap;
	}

}
