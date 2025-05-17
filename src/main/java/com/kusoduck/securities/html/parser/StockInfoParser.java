package com.kusoduck.securities.html.parser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kusoduck.stock.dao.StockInfoDAO;
import com.kusoduck.utils.ParseHtmlUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kusoduck
 */
public class StockInfoParser {
	private static Logger logger = LoggerFactory.getLogger(StockInfoParser.class);

	private static final String URL = "http://isin.twse.com.tw/isin/C_public.jsp?strMode=2";

	private StockInfoParser() {

	}

	public static void parse(Connection conn) {
		logger.debug(String.format("URL: %s", URL));
		try {
			// Need jsoup.jar from http://jsoup.org/
			Document doc = ParseHtmlUtils.getDocument(URL);

			Elements tableElements = doc.getElementsByTag("table");
			for (Element tableElement : tableElements) {
				Elements trElements = tableElement.getElementsByTag("tr");
				for (int i = 2; i < trElements.size(); i++) {
					List<String> content = new ArrayList<>();
					Elements tdElements = trElements.get(i).getElementsByTag("td");
					for (Element tdElement : tdElements) {
						content.add(tdElement.text());
					}
					if (content.get(0).equals("上市認購(售)權證")) {
						break;
					}
					StockInfoDAO.insert(conn, content);
				}
			}

			conn.commit();
		} catch (IOException | SQLException e) {
			logger.error(e.getMessage());
		}
	}
}
