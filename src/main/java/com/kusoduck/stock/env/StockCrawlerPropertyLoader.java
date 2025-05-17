package com.kusoduck.stock.env;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StockCrawlerPropertyLoader {
	private static String propFileName = System.getProperty("prop.crawler");
	private static Properties prop = new Properties();

	private StockCrawlerPropertyLoader() {
	}

	public static void init() throws IOException {
		FileReader fileReader = new FileReader(propFileName);
		prop.load(fileReader);
	}

		public static String getIncomeStatementUrl() {
			return prop.getProperty("income.statement.url");
		}
}
