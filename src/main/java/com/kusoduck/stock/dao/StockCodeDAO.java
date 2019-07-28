package com.kusoduck.stock.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class StockCodeDAO {
	private static final String SELECT = "SELECT 股票代號 FROM stockcode";
	private static final String INSERT = "INSERT INTO stockcode(股票代號, 股票名稱, 上市日, 市場別, 產業別) VALUES(?, ?, ?, ?, ?)";
	private static Logger logger;

	private StockCodeDAO() {

	}

	public static Set<String> select(Connection conn) {
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(SELECT)) {
				Set<String> stockIds = new HashSet<>();
				while (rs.next()) {
					stockIds.add(rs.getString(1));
				}
				return stockIds;
			}
		} catch (SQLException e) {
			logger.log(null, e.getMessage());
			return new HashSet<>();
		}
	}

	public static void insertSQL(Connection conn, String[] col) {
		try (PreparedStatement preState = conn.prepareStatement(INSERT)) {
			preState.setString(1, col[0].substring(0, 4) );
			preState.setString(2, col[0].substring(4, col[0].length() ).trim());
			preState.setString(3, col[2]);
			preState.setString(4, col[3]);
			preState.setString(5, col[4]);
			preState.executeUpdate();

		} catch (SQLException e) {
			logger.log(null, e.getMessage());
		}
	}
}
