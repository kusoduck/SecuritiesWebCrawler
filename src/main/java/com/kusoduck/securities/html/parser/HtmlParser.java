package com.kusoduck.securities.html.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class HtmlParser<T, H> {

	public abstract List<T> parse(String url, Object... context);

	protected Map<Integer, H> getColumnNumberHeaderMap(Element tableElement) {
		Map<Integer, H> colNumHeaderMap = new HashMap<>();
		Elements headRow = tableElement.select("tr.tblHead");
		Elements thElements = headRow.get(0).select("th");
		for (Element thElement : thElements) {
			populateColNumHeaderMap(colNumHeaderMap, thElement); // 抽象方法: 填充欄位名稱
		}
		return colNumHeaderMap;
	}

	// 共用的遍歷邏輯 (抽出原本 getData 的外迴圈)
	protected List<T> extractData(Element targetTableElement, Map<Integer, H> colNumHeaderMap, Object... context) {
		List<T> dataList = new ArrayList<>();
		for (Element tbodyElement : targetTableElement.getElementsByTag("tbody")) {
//			for (Element trElement : tbodyElement.getElementsByTag("tr")) {
			for (Element trElement : tbodyElement.select("tr.even")) {
				T entity = createEntity(context); // 抽象方法：建立物件實例

				Elements tdElements = trElement.getElementsByTag("td");
				for (Element tdElement : tdElements) {
					int index = tdElement.elementSiblingIndex();
					H header = colNumHeaderMap.get(index);
					String value = tdElement.text();

					if (header != null) {
						// 呼叫子類別實作的填充邏輯
						populateEntity(entity, header, value);
					}
				}
				dataList.add(entity);
			}
		}
		return dataList;
	}

	protected abstract void populateColNumHeaderMap(Map<Integer, H> colNumHeaderMap, Element thElement);

	// 讓子類別決定怎麼產生物件 (例如：return new StockDailyQuote())
	protected abstract T createEntity(Object... context);

	// 讓子類別決定怎麼把 value 塞進 entity (取代原本的 switch-case)
	protected abstract void populateEntity(T entity, H header, String value);
}
