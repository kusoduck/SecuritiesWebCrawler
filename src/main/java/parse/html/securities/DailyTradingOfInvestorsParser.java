/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parse.html.securities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.parse.html.ParseHtmlUtils;

public class DailyTradingOfInvestorsParser {
	private static Logger logger = Logger.getLogger(DailyTradingOfInvestorsParser.class);

	private static final String URL = "http://www.twse.com.tw/fund/T86?response=html&selectType=ALLBUT0999&date=";

	public void prase(Connection conn, String date) {
		String url = URL + date;
		logger.debug(String.format("%s Daily Trading Details of Foreign and Other Investors", date));
		logger.debug(String.format("URL: %s", url));
		try {
			// Need jsoup.jar from http://jsoup.org/
			Document doc = ParseHtmlUtils.getDocument(url);
			Elements tableElements = doc.getElementsByTag("table");
			for (Element tableElement : tableElements) {
				Elements tbodyElements = tableElement.getElementsByTag("tbody");
				for (Element tbodyElement : tbodyElements) {
					Elements trElements = tbodyElement.getElementsByTag("tr");
					for (Element trElement : trElements) {
						Elements tdElements = trElement.getElementsByTag("td");
						List<String> dailyTradingOfInvestorColumns = new ArrayList<>();
						for (Element tdElement : tdElements) {
							dailyTradingOfInvestorColumns.add(tdElement.text());

						}
					}
				}
			}

			conn.commit();

		} catch (IOException | SQLException e) {
			logger.error(e.getMessage());
		}
	}

	private void insertSQL(Connection conn, String[] col) {
		String sql = "INSERT INTO corporate(�蟡其誨���, ����, 憭�眺�脰�, 憭�都���, ��縑鞎琿�脰�, ��縑鞈����, ����眺�脰�, ����都���, ����眺�脰���, ����都�����, 銝之瘜犖鞎瑁都頞�) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, col[1]);
			ps.setString(2, col[0]);
			ps.setInt(3, Integer.parseInt(col[3].replaceAll(",", "")));
			ps.setInt(4, Integer.parseInt(col[4].replaceAll(",", "")));
			ps.setInt(5, Integer.parseInt(col[6].replaceAll(",", "")));
			ps.setInt(6, Integer.parseInt(col[7].replaceAll(",", "")));
			ps.setInt(7, Integer.parseInt(col[10].replaceAll(",", "")));
			ps.setInt(8, Integer.parseInt(col[11].replaceAll(",", "")));
			ps.setInt(9, Integer.parseInt(col[13].replaceAll(",", "")));
			ps.setInt(10, Integer.parseInt(col[14].replaceAll(",", "")));
			ps.setInt(11, Integer.parseInt(col[16].replaceAll(",", "")));

			ps.addBatch();
			ps.executeBatch();
			ps.close();
		} catch (SQLException ex) {
		}
	}

	private void insertSQL2(Connection conn, String[] col) {
		String sql = "INSERT INTO corporate(�蟡其誨���, ����, 憭�眺�脰�, 憭�都���, ��縑鞎琿�脰�, ��縑鞈����, ����眺�脰�, ����都���, ����眺�脰���, ����都�����, 銝之瘜犖鞎瑁都頞�) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, col[1]);
			ps.setString(2, col[0]);
			ps.setInt(3, Integer.parseInt(col[3].replaceAll(",", "")));
			ps.setInt(4, Integer.parseInt(col[4].replaceAll(",", "")));
			ps.setInt(5, Integer.parseInt(col[9].replaceAll(",", "")));
			ps.setInt(6, Integer.parseInt(col[10].replaceAll(",", "")));
			ps.setInt(7, Integer.parseInt(col[13].replaceAll(",", "")));
			ps.setInt(8, Integer.parseInt(col[14].replaceAll(",", "")));
			ps.setInt(9, Integer.parseInt(col[16].replaceAll(",", "")));
			ps.setInt(10, Integer.parseInt(col[17].replaceAll(",", "")));
			ps.setInt(11, Integer.parseInt(col[19].replaceAll(",", "")));

			ps.addBatch();
			ps.executeBatch();
			ps.close();

		} catch (SQLException ex) {
		}
	}

}
