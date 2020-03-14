package stock.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import common.dao.CommonDAO;
import common.dao.DbTableDataBean;
import stock.constant.DailyQuotesColumn;

public class DailyQuotesDAO {
	private static Logger logger = Logger.getLogger(DailyQuotesDAO.class);
	private static final String TABLE = "daily_quotes";

	private DailyQuotesDAO() {

	}

	public static void create(Connection conn, String date, List<Map<DailyQuotesColumn, String>> dailyQuotes) {
		for (Map<DailyQuotesColumn, String> stockDailyQuotesMap : dailyQuotes) {
			List<DbTableDataBean> dataBean = new ArrayList<>();
			dataBean.add(new DbTableDataBean("date", date));
			for (Entry<DailyQuotesColumn, String> stockDailyQuotesEntry : stockDailyQuotesMap.entrySet()) {
				String value = stockDailyQuotesEntry.getValue().replace(",", "");
				DailyQuotesColumn dailyQuotesColumn = stockDailyQuotesEntry.getKey();
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
