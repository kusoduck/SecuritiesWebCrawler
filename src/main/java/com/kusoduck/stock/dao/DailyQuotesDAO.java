package com.kusoduck.stock.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;

import common.dao.CommonDao;
import common.dao.DbDataTypeEnum;
import common.dao.DbTableDataBean;
import stock.constant.DailyQuotesColumn;

public class DailyQuotesDAO {
	private static final String TABLE = "daily_quetes";

	public static void create(Connection conn, String date, List<Map<DailyQuotesColumn, String>> stockDailyQuotes) {

		for (Map<DailyQuotesColumn, String> stockDailyQuotesMap : stockDailyQuotes) {
			List<DbTableDataBean> dataBean = new ArrayList<>();
			dataBean.add(new DbTableDataBean("日期", date, DbDataTypeEnum.STRING));
			for (Entry<DailyQuotesColumn, String> stockDailyQuotesEntry : stockDailyQuotesMap.entrySet()) {
				String value = stockDailyQuotesEntry.getValue().replaceAll(",", "");
				DailyQuotesColumn dailyQuotesColumn = stockDailyQuotesEntry.getKey();
				switch (dailyQuotesColumn) {
				case SECURITY_CODE:
				case SECURITY_NAME:
				case DIRECTION:
					dataBean.add(new DbTableDataBean(dailyQuotesColumn.getChinese(), value, DbDataTypeEnum.STRING));
					break;
				default:
					if (NumberUtils.isCreatable(value)) {
						dataBean.add(new DbTableDataBean(dailyQuotesColumn.getChinese(), value, DbDataTypeEnum.STRING));
					} else {
						dataBean.add(new DbTableDataBean(dailyQuotesColumn.getChinese(), null, DbDataTypeEnum.STRING));
					}
					break;
				}
			}
			CommonDao.create(conn, TABLE, dataBean);
		}
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
