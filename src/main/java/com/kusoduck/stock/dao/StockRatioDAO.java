package com.kusoduck.stock.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kusoduck.common.dao.CommonDAO;
import com.kusoduck.common.dao.DbTableDataBean;
import com.kusoduck.stock.constant.StockRatiosColumn;

public class StockRatioDAO {
	private static Logger logger = LoggerFactory.getLogger(StockRatioDAO.class);

	private static final String TABLE = "t_stock_ratio";

	private StockRatioDAO() {

	}

	public static void create(Connection conn, String date, List<Map<StockRatiosColumn, String>> stockRatios) {
		for (Map<StockRatiosColumn, String> stockRatiosMap : stockRatios) {
			List<DbTableDataBean> dataBean = new ArrayList<>();
			dataBean.add(new DbTableDataBean("TRADE_DATE", date));
			for (Entry<StockRatiosColumn, String> stockRatiosEntry : stockRatiosMap.entrySet()) {
				String value = stockRatiosEntry.getValue().replace(",", "");
				StockRatiosColumn ratiosOfSecuritiesColumn = stockRatiosEntry.getKey();
				switch (ratiosOfSecuritiesColumn) {
				case SECURITY_CODE:
				case SECURITY_NAME:
				case DIVIDEND_YEAR:
				case FINANCIAL_YEAR_QUARTER:
					dataBean.add(new DbTableDataBean(ratiosOfSecuritiesColumn.name(), value));
					break;
				default:
					if (NumberUtils.isCreatable(value)) {
						dataBean.add(new DbTableDataBean(ratiosOfSecuritiesColumn.name(), value));
					} else {
						dataBean.add(new DbTableDataBean(ratiosOfSecuritiesColumn.name(), null));
					}
					break;
				}
			}
			CommonDAO.create(conn, TABLE, dataBean);
			try {
				conn.commit();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
