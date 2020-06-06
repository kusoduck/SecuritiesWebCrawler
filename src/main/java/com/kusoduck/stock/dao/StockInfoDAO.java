package com.kusoduck.stock.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class StockInfoDAO {
	private static Logger logger = Logger.getLogger(StockInfoDAO.class);

	private static final String SELECT = "SELECT security_code FROM stock_info";
	private static final String INSERT = "INSERT INTO stock_info(security_code, security_name, listing_date, market, industry) VALUES(?, ?, ?, ?, ?)";

	private StockInfoDAO() {

	}

	public static List<String> select(Connection conn) {
		List<String> stockCodes = new ArrayList<>();
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(SELECT)) {
				while (rs.next()) {
					stockCodes.add(rs.getString(1));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return stockCodes;
	}

	public static int insert(Connection conn, List<String> values) {
		int count = 0;
		try (PreparedStatement preState = conn.prepareStatement(INSERT)) {
			int i = 1;
			String[] value0 = values.get(0).split("　");
			preState.setString(i++, value0[0]);
			preState.setString(i++, value0[1]);
			preState.setString(i++, values.get(2));
			preState.setString(i++, values.get(3));
			preState.setString(i++, values.get(4));
			count = preState.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return count;
	}
}
