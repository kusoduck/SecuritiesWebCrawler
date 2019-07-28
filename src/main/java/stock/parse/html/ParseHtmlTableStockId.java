package stock.parse.html;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import getstockdata.MysqlConn;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kusoduck
 */
public class ParseHtmlTableStockId {
	static Connection conn = null;

	public ParseHtmlTableStockId() {
		MysqlConn connecter = new MysqlConn();
		conn = connecter.getConn();

		String html = "http://isin.twse.com.tw/isin/C_public.jsp?strMode=2";
		praseHTMLTable(html);
	}

	public static void praseHTMLTable(String html) {
		try {
			//Need jsoup.jar  from http://jsoup.org/
			Document doc = Jsoup.connect(html).get();
			Elements tableElements = doc.getAllElements();
			/*
        Elements tableHeaderEles = tableElements.select("thead tr th");
        System.out.println("headers");
        for (int i = 0; i < tableHeaderEles.size(); i++) {
           System.out.println(tableHeaderEles.get(i).text());
        }
        System.out.println();
			 */
			Elements tableRowElements = tableElements.select("tr");

			for (int i=2; i < tableRowElements.size(); i++) {
				Element row = tableRowElements.get(i);
				Elements rowItems = row.select("td");
				if(rowItems.get(0).text().equals("銝��頃(�)甈��")) {
					break;
				}

				String[] col = new String[7];
				for (int j = 0; j < rowItems.size(); j++) {
					//System.out.println(rowItems.get(j).text());
					col[j] = rowItems.get(j).text();
				}
				System.out.println(col[0]);
				insertSQL(col);
			}
			try {
				conn.commit();
			} catch (SQLException ex) {
				System.out.println(ex.toString());
			}

		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	private static void insertSQL(String[] col) {
		String sql = "INSERT INTO stockcode(�蟡其誨���, �蟡典�迂, 銝�, 撣�, �璆剖) VALUES(?, ?, ?, ?, ?)";
		//System.out.println(col[0].substring(0, 4));
		//System.out.println(col[0].substring(6, col[0].length()));

		try (PreparedStatement preState = conn.prepareStatement(sql)) {
			preState.setString(1, col[0].substring(0, 4) );
			preState.setString(2, col[0].substring(4, col[0].length() ).trim().replaceAll("��", "") );
			preState.setString(3, col[2]);
			preState.setString(4, col[3]);
			preState.setString(5, col[4]);
			preState.executeUpdate();

		} catch (SQLException ex) {
			//ex.printStackTrace();
		}
	}
}
