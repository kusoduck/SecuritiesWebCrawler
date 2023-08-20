package com.kusoduck.stock.crawler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.kusoduck.stock.po.EpsPO;

public class EpsCrawler {
	//	private static Logger logger = Logger.getLogger(EpsCrawler.class);

	//	public static void main(String[] args) {
	//		crawl(LocalDate.now());
	//	}

	public static List<EpsPO> crawl(int year, int season) {
		try {
			String url = "https://mops.twse.com.tw/mops/web/ajax_t163sb04";
			Document document = Jsoup.connect(url).data("encodeURIComponent", "1").data("step", "1")
					.data("firstin", "1").data("off", "1").data("TYPEK", "sii").data("year", String.valueOf(year))
					.data("season", String.valueOf(season)).post();

			List<EpsPO> epsPOs = new ArrayList<>();
			Elements tables = document.select("table.hasBorder");
			for (Element table : tables) {
				Map<Integer, String> columnIndexTitleMap = new HashMap<>();
				headerCrawler(table, columnIndexTitleMap);

				Elements dataRows = table.select("tr.odd");
				if (dataRows.isEmpty()) {
					dataRows = table.select("tr.even");
				}
				dataCrawler(columnIndexTitleMap, dataRows, epsPOs, year, season);
			}

			return epsPOs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void dataCrawler(Map<Integer, String> columnIndexTitleMap, Elements dataRows, List<EpsPO> epsPOs, int year, int season) {
		for (Element dataRow : dataRows) {
			EpsPO epsPO = new EpsPO();
			Elements datas = dataRow.select("td");
			int i = 0;
			for (Element data : datas) {
				switch (columnIndexTitleMap.get(i++)) {
				case "公司 代號":
					epsPO.setSecurityCode(data.text());
					break;
					// case "收益":
					// case "營業收入":
					// epsPO.setOperatingIncome(new BigDecimal(data.text()));
					// break;
					// case "營業利益（損失）":
					// case "營業利益":
					// case "繼續營業單位稅前淨利（淨損）":
					// epsPO.setOperatingProfitLoss(new BigDecimal(data.text()));
					// break;
				case "基本每股盈餘（元）":
					epsPO.setEps(new BigDecimal(data.text()));
					break;
				default:
					break;
				}
			}
			epsPO.setYear(year+1911);
			epsPO.setSeason(season);
			epsPOs.add(epsPO);
		}
	}

	private static void headerCrawler(Element table, Map<Integer, String> columnIndexTitleMap) {
		Elements headRow = table.select("tr.tblHead");
		Elements header = headRow.get(0).select("th");
		int i = 0;
		for (Element head : header) {
			//			System.out.println(head.text());
			columnIndexTitleMap.put(i++, head.text());
		}
	}
}
