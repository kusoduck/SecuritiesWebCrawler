package common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

public class CommonDAO {
	private static Logger logger = Logger.getLogger(CommonDAO.class);

	private CommonDAO() {

	}

	public static int create(Connection conn, String tableName, List<DbTableDataBean> cols) {
		StringBuilder insertSQL = new StringBuilder();
		insertSQL.append("INSERT INTO ").append(tableName).append("(");
		StringBuilder insertValue = new StringBuilder();
		insertValue.append(") VALUES(");
		int size = cols.size();
		int i = 0;
		for (DbTableDataBean bean : cols) {
			insertSQL.append("`");
			insertSQL.append(bean.columnName);
			insertSQL.append("`");
			insertValue.append("?");
			i++;
			if (i < size) {
				insertSQL.append(",");
				insertValue.append(",");
			}
		}
		insertSQL.append(insertValue).append(");");

		i = 0;
		try (PreparedStatement ps = conn.prepareStatement(insertSQL.toString())) {
			for (DbTableDataBean bean : cols) {
				i++;
				ps.setString(i, bean.columnValue);
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			for (DbTableDataBean bean : cols) {
				logger.debug(String.format("%s:%s", bean.getColumnName(), bean.getColumnValue()));
			}
			return 0;
		}
	}

	public static int update(Connection conn, String tableName, List<DbTableDataBean> cols,
			List<DbTableDataBean> condition) {
		int updateCount = 0;
		boolean isFirst = true;
		StringBuilder updateSQL = new StringBuilder();

		updateSQL.append("UPDATE ").append(tableName).append(" SET ");
		for (DbTableDataBean colBean : cols) {
			if (isFirst) {
				isFirst = false;
			} else {
				updateSQL.append(",");
			}
			updateSQL.append(colBean.columnName).append("=").append("?");
		}

		updateSQL.append(" WHERE ");
		isFirst = true;
		for (DbTableDataBean conditionBean : condition) {
			if (isFirst) {
				isFirst = false;
			} else {
				updateSQL.append(" AND ");
			}
			updateSQL.append(conditionBean.getColumnName()).append("=").append("?");
		}

		try (PreparedStatement ps = conn.prepareStatement(updateSQL.toString())) {
			int i = 1;
			for (DbTableDataBean colBean : cols) {
				ps.setString(i++, colBean.columnValue);
			}
			for (DbTableDataBean conditionBean : condition) {
				ps.setString(i++, conditionBean.columnValue);
			}
			updateCount = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			for (DbTableDataBean bean : cols) {
				logger.debug(String.format("%s:%s", bean.getColumnName(), bean.getColumnValue()));
			}
		}
		return updateCount;
	}
}
