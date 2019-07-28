package com.kusoduck.stock.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;

import common.dao.CommonDao;
import common.dao.DbDataTypeEnum;
import common.dao.DbTableDataBean;
import stock.constant.RatiosOfSecuritiesColumn;

public class StockRatioDAO {
	private static final String TABLE = "stock_ratio";

	public static void create(Connection conn, String date, List<Map<RatiosOfSecuritiesColumn, String>> stockRatios) {
		for (Map<RatiosOfSecuritiesColumn, String> stockRatiosMap : stockRatios) {
			List<DbTableDataBean> dataBean = new ArrayList<>();
			dataBean.add(new DbTableDataBean("日期", date, DbDataTypeEnum.STRING));
			for (Entry<RatiosOfSecuritiesColumn, String> stockRatiosEntry : stockRatiosMap.entrySet()) {
				String value = stockRatiosEntry.getValue().replaceAll(",", "");
				RatiosOfSecuritiesColumn ratiosOfSecuritiesColumn = stockRatiosEntry.getKey();
				switch (ratiosOfSecuritiesColumn) {
				case SECURITY_CODE:
				case SECURITY_NAME:
				case DIVIDEND_YEAR:
				case FINANCIAL_YEAR_QUARTER:
					dataBean.add(
							new DbTableDataBean(stockRatiosEntry.getKey().getChinese(), value, DbDataTypeEnum.STRING));
					break;
				default:
					if (NumberUtils.isCreatable(value)) {
						dataBean.add(new DbTableDataBean(stockRatiosEntry.getKey().getChinese(), value,
								DbDataTypeEnum.STRING));
					} else {
						dataBean.add(new DbTableDataBean(stockRatiosEntry.getKey().getChinese(), null,
								DbDataTypeEnum.STRING));
					}
					break;
				}
			}
			CommonDao.create(conn, TABLE, dataBean);
		}
	}
}
