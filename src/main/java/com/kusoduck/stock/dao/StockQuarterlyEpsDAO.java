package com.kusoduck.stock.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.kusoduck.stock.po.StockQuarterlyEpsPO;


public class StockQuarterlyEpsDAO {

	public static void create(Connection conn, StockQuarterlyEpsPO po) throws SQLException {
		String sql = "INSERT INTO securities.t_stock_quarterly_eps (stock_symbol, report_year, quarter, eps) VALUES (?, ?, ?, ?);";

		Object[] params = { po.getStockSymbol(), po.getReportYear(), po.getQuarter(), po.getEps() };

		QueryRunner queryRunner = new QueryRunner();
		queryRunner.update(conn, sql, params);
	}
}
