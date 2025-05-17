package com.kusoduck.stock.crawler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.kusoduck.stock.dao.EpsDAO;
import com.kusoduck.stock.env.MySQLConnector;
import com.kusoduck.stock.env.StockCrawlerPropertyLoader;
import com.kusoduck.stock.po.EpsPO;

public class EpsCrawlerTest {

	@BeforeAll
	public static void init() throws IOException {
		System.setProperty("prop.database", "src/main/resources/properties/Database.properties");
		System.setProperty("prop.crawler", "src/main/resources/properties/StockCrawler.properties");
		StockCrawlerPropertyLoader.init();
	}

	@Test
	public void testCrawl() throws SQLException {
		int year = 2022;
		int season = 2;
		List<EpsPO> epsPOs = EpsCrawler.crawl(year-1911, season);
		try (Connection conn = MySQLConnector.getConn("securities")) {
			List<EpsPO> dbEpsPO = EpsDAO.find(conn, year, season);
			epsPOs = (List<EpsPO>) CollectionUtils.removeAll(epsPOs, dbEpsPO);
			EpsDAO.insert(conn, epsPOs);
			conn.commit();
		}
	}
}
