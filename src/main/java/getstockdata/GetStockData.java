/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getstockdata;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.collections4.CollectionUtils;

import com.kusoduck.stock.dao.DailyQuotesDAO;
import com.kusoduck.stock.dao.StockRatioDAO;

import stock.constant.DailyQuotesColumn;
import stock.constant.RatiosOfSecuritiesColumn;
import stock.parse.html.ParseHtmlDailyQuotesStockExchange;
import stock.parse.html.ParseHtmlStockRatiosStockExchange;

public class GetStockData {

	public static void main(String[] args) {
		String startDate = "";
		System.out.println("(1.)匯入今日資料");
		System.out.println("(2.)從指定日期開始");
		System.out.println("(3.)從201501開始");
		String option;
		Scanner scanner = new Scanner(System.in);
		option = scanner.nextLine();

		if (option.compareToIgnoreCase("2") == 0) {
			System.out.println("輸入年月日(YYYYMMDD)");
			startDate = scanner.nextLine();
		} else if (option.compareToIgnoreCase("3") == 0) {
			startDate = "20150101";
		}

		MysqlConn connecter = new MysqlConn();
		Connection conn = connecter.getConn();

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
		try {
			switch (option) {
			case "1":
				startDate = formatDate.format(new Date());
				parseHtmlAndInsert2Db(formatDate.format(new Date()), conn);
				break;
			case "2":
			case "3":
				Date date1 = formatDate.parse(startDate);
				Date date2 = new Date();
				long diff = date2.getTime() - date1.getTime();
				long days = diff / (1000 * 60 * 60 * 24);

				Calendar calen = Calendar.getInstance();
				calen.setTime(date1);
				if (days != 0) {
					for (int i = 0; i <= days; i++) {
						date1 = calen.getTime();
						parseHtmlAndInsert2Db(formatDate.format(date1), conn);
						calen.add(Calendar.DATE, 1);
					}
				} else {
					parseHtmlAndInsert2Db(startDate, conn);
				}
				break;
			default:
				break;
			}
			System.out.println("Finish");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		scanner.close();
	}

	private static void parseHtmlAndInsert2Db(String date, Connection conn) {
		List<Map<DailyQuotesColumn, String>> stockDailyQuotes = ParseHtmlDailyQuotesStockExchange.praseHTMLTable(date);
		if (CollectionUtils.isNotEmpty(stockDailyQuotes)) {
			DailyQuotesDAO.create(conn, date, stockDailyQuotes);
		} else {
			System.out.println("no data in " + date);
		}

		List<Map<RatiosOfSecuritiesColumn, String>> stockRatios = ParseHtmlStockRatiosStockExchange
				.praseHTMLTable(date);
		if (CollectionUtils.isNotEmpty(stockRatios)) {
			StockRatioDAO.create(conn, date, stockRatios);
		} else {
			System.out.println("no data in " + date);
		}
	}
}
