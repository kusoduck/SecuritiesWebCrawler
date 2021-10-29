package com.kusoduck.stock.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.kusoduck.common.dao.CommonDAO;
import com.kusoduck.common.dao.DbTableDataBean;
import com.kusoduck.stock.constant.StockDailyQuotesColumn;

public class StockDailyQuotesDAO {
	private static Logger logger = Logger.getLogger(StockDailyQuotesDAO.class);
	private static final String TABLE = "t_daily_quotes";

	private StockDailyQuotesDAO() {

	}

	public static void create(Connection conn, String date, List<Map<StockDailyQuotesColumn, String>> dailyQuotes) {
		for (Map<StockDailyQuotesColumn, String> stockDailyQuotesMap : dailyQuotes) {
			List<DbTableDataBean> dataBean = new ArrayList<>();
			dataBean.add(new DbTableDataBean("TRADE_DATE", date));
			for (Entry<StockDailyQuotesColumn, String> stockDailyQuotesEntry : stockDailyQuotesMap.entrySet()) {
				String value = stockDailyQuotesEntry.getValue().replace(",", "");
				StockDailyQuotesColumn dailyQuotesColumn = stockDailyQuotesEntry.getKey();
				switch (dailyQuotesColumn) {
				case SECURITY_CODE:
				case SECURITY_NAME:
				case DIRECTION:
					dataBean.add(new DbTableDataBean(dailyQuotesColumn.name(), value));
					break;
				default:
					if (NumberUtils.isCreatable(value)) {
						dataBean.add(new DbTableDataBean(dailyQuotesColumn.name(), value));
					} else {
						dataBean.add(new DbTableDataBean(dailyQuotesColumn.name(), null));
					}
					break;
				}
			}
			CommonDAO.create(conn, TABLE, dataBean);
		}
		try {
			conn.commit();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
}
