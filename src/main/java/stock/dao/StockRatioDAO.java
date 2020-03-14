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
import stock.constant.StockRatiosTitle;

public class StockRatioDAO {
	private static Logger logger = Logger.getLogger(StockRatioDAO.class);

	private static final String TABLE = "stock_ratio";

	private StockRatioDAO() {

	}

	public static void create(Connection conn, String date, List<Map<StockRatiosTitle, String>> stockRatios) {
		for (Map<StockRatiosTitle, String> stockRatiosMap : stockRatios) {
			List<DbTableDataBean> dataBean = new ArrayList<>();
			dataBean.add(new DbTableDataBean("date", date));
			for (Entry<StockRatiosTitle, String> stockRatiosEntry : stockRatiosMap.entrySet()) {
				String value = stockRatiosEntry.getValue().replace(",", "");
				StockRatiosTitle ratiosOfSecuritiesColumn = stockRatiosEntry.getKey();
				switch (ratiosOfSecuritiesColumn) {
				case CODE:
				case NAME:
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
