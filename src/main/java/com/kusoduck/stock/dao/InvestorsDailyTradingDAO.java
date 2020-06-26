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
import com.kusoduck.stock.constant.InvestorsDailyTradingColumn;

public class InvestorsDailyTradingDAO {
	private static Logger logger = Logger.getLogger(InvestorsDailyTradingDAO.class);

	private static final String TABLE = "t_investors_daily_trading";

	public static void create(Connection conn, String date, List<Map<InvestorsDailyTradingColumn, String>> investorsDailyTradings) {
		for (Map<InvestorsDailyTradingColumn, String> investorsDailyTradingMap : investorsDailyTradings) {
			List<DbTableDataBean> dataBean = new ArrayList<>();
			dataBean.add(new DbTableDataBean("TRADE_DATE", date));
			for (Entry<InvestorsDailyTradingColumn, String> investorsDailyTradingEntry : investorsDailyTradingMap.entrySet()) {
				String value = investorsDailyTradingEntry.getValue().replace(",", "");
				InvestorsDailyTradingColumn investorsDailyTradingColumn = investorsDailyTradingEntry.getKey();
				switch (investorsDailyTradingColumn) {
				case SECURITY_CODE:
				case SECURITY_NAME:
					dataBean.add(new DbTableDataBean(investorsDailyTradingColumn.name(), value));
					break;
				default:
					if (NumberUtils.isCreatable(value)) {
						dataBean.add(new DbTableDataBean(investorsDailyTradingColumn.name(), value));
					} else {
						dataBean.add(new DbTableDataBean(investorsDailyTradingColumn.name(), null));
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
