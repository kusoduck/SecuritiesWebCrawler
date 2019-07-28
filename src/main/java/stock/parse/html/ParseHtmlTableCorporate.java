/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.parse.html;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.kusoduck.stock.dao.StockCodeDAO;
import com.kusoduck.stock.dao.TradeDateDAO;

import getstockdata.MysqlConn;

public class ParseHtmlTableCorporate {
	private Connection conn = null;

	public ParseHtmlTableCorporate(int startMonth, int startYear) {
		try {
			// TODO code application logic here
			MysqlConn connecter = new MysqlConn();
			conn = connecter.getConn();
			String html = "http://www.twse.com.tw/fund/T86?response=html&selectType=ALLBUT0999&date=";
			String formatStr = "%02d";

			SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String strDate = formatDate.format(date);

			String startDate = "" + startYear + String.format(formatStr, startMonth) + "01", endDate = strDate;

			List<String> listDate = TradeDateDAO.select(conn, startDate, endDate);

			for (String temp : listDate) {
				// strDate = convDate(temp.replace("-", ""),"ch",1);
				strDate = temp.replace("-", "");
				// System.out.println(html+strDate);
				praseHTMLTable(html + strDate, strDate);
			}

			System.out.println("------------------");
			System.out.println("Finish insert stmt");
			conn.close();
		} catch (SQLException ex) {
			Logger.getLogger(ParseHtmlStockRatiosStockExchange.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void praseHTMLTable(String html, String date) throws SQLException {
		try {
			// Need jsoup.jar from http://jsoup.org/
			Document doc = Jsoup.connect(html).maxBodySize(0).timeout(20 * 1000).get();

			System.out.println(date);
			Elements tableElements = doc.getElementsByTag("table");
			Elements tableRowElements = tableElements.select("tr");

			Set<String> stockIds = StockCodeDAO.select(conn);

			String col[] = new String[20];
			col[0] = convDate(date, "ad", 0);

			for (int i = 2; i < tableRowElements.size(); i++) {
				// System.out.println("row " + i);
				Element row = tableRowElements.get(i);
				Elements rowItems = row.select("td");

				if (stockIds.contains(rowItems.get(0).text())) {
					// System.out.println(rowItems.get(0).text());
					for (int j = 0; j < rowItems.size(); j++) {
						col[j + 1] = rowItems.get(j).text().replace("</", "");
						// System.out.println("j=" + j + " "+ col[j+1]);
					}
					if (Integer.valueOf(col[0]) > 20171215) {
						insertSQL2(col);
					}
					insertSQL(col);
				}
			}
			commit();
			Thread.sleep(5000);
		} catch (IOException ex) {
			System.out.println(ex.toString());
		} catch (InterruptedException ex) {
			Logger.getLogger(ParseHtmlTableCorporate.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void insertSQL(String[] col) {
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
			// System.out.println(ex.toString());
		}
	}

	private void insertSQL2(String[] col) {
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
			// System.out.println(ex.toString());
		}
	}

	private void commit() {
		try {
			conn.commit();
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}
	}

	public String convDate(String date, String type, int slash) {
		int length = date.length();
		String d = null, m = null, y = null, temp;
		switch (length) {
		case 8:
			y = date.substring(0, 4);
			m = date.substring(4, 6);
			d = date.substring(6, 8);
			if (type == "ch") {
				y = "" + (Integer.parseInt(y) - 1911);
			}
			break;
		case 10:
			temp = date.replace("/", "");
			y = temp.substring(0, 4);
			m = temp.substring(4, 6);
			d = temp.substring(6, 8);
			if (type == "ch") {
				y = "" + (Integer.parseInt(y) - 1911);
			}
			break;
		case 7:
			y = date.substring(0, 3);
			m = date.substring(3, 5);
			d = date.substring(5, 7);
			if (type == "ad") {
				y = "" + (Integer.parseInt(y) + 1911);
			}
			break;
		case 9:
			temp = date.replace("/", "");
			y = temp.substring(0, 3);
			m = temp.substring(3, 5);
			d = temp.substring(5, 7);
			if (type == "ad") {
				y = "" + (Integer.parseInt(y) + 1911);
			}
			break;
		}

		String convDate = null;
		if (slash == 0) {
			convDate = y + m + d;
		} else if (slash == 1) {
			convDate = y + "/" + m + "/" + d;
		}
		return convDate;
	}
}
