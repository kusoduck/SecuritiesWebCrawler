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
import com.kusoduck.stock.constant.IndexDailyQuotesColumn;

public class IndexDailyQuotesDAO {
	private static Logger logger = Logger.getLogger(IndexDailyQuotesDAO.class);
	private static final String TABLE = "t_indice_daily_quotes";

	private IndexDailyQuotesDAO() {

	}

	public static void create(Connection conn, String date, List<Map<IndexDailyQuotesColumn, String>> indiceDailyQuotes) {
		for (Map<IndexDailyQuotesColumn, String> indiceDailyQuotesMap : indiceDailyQuotes) {
			List<DbTableDataBean> dataBean = new ArrayList<>();
			dataBean.add(new DbTableDataBean("DATE", date));
			for (Entry<IndexDailyQuotesColumn, String> indiceDailyQuotesEntry : indiceDailyQuotesMap.entrySet()) {
				String value = indiceDailyQuotesEntry.getValue().replace(",", "");
				IndexDailyQuotesColumn indiceDailyQuotesColumn = indiceDailyQuotesEntry.getKey();
				switch (indiceDailyQuotesColumn) {
				case INDEX_NAME:
				case DIRECTION:
					dataBean.add(new DbTableDataBean(indiceDailyQuotesColumn.name(), value));
					break;
				default:
					if (NumberUtils.isCreatable(value)) {
						dataBean.add(new DbTableDataBean(indiceDailyQuotesColumn.name(), value));
					} else {
						dataBean.add(new DbTableDataBean(indiceDailyQuotesColumn.name(), null));
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
