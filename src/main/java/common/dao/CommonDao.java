package common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CommonDao {

	private CommonDao() {

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
				switch (bean.dataType) {
				case STRING:
					ps.setString(i, bean.columnValue);
					break;
				case LONG:
					ps.setLong(i, Long.parseLong(bean.columnValue));
					break;
				case INT:
					ps.setInt(i, Integer.parseInt(bean.columnValue));
					break;
				case FLOAT:
					ps.setFloat(i, Float.parseFloat(bean.columnValue));
					break;
				default:
					break;
				}
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(insertSQL);
			for (DbTableDataBean bean : cols) {
				System.out.println(bean.getColumnName() + " : " + bean.getColumnValue());
			}
			return 0;
		}
	}

	public static int update(Connection conn, String tableName, List<DbTableDataBean> cols,
			List<DbTableDataBean> condition) throws SQLException {
		StringBuilder updateSQL = new StringBuilder();
		updateSQL.append("UPDATE ").append(tableName).append(" SET ");
		int colsSize = cols.size();
		int i = 0;
		for (DbTableDataBean colBean : cols) {
			updateSQL.append(colBean.columnName).append("=").append("?");
			i++;
			if (i < colsSize) {
				updateSQL.append(",");
			}
		}
		int conditionSize = condition.size();
		i = 0;
		updateSQL.append(" WHERE ");
		for (DbTableDataBean conditionBean : condition) {
			updateSQL.append(conditionBean.getColumnName()).append("=").append("?");
			i++;
			if (i < conditionSize) {
				updateSQL.append(" AND ");
			}
		}

		i = 0;
		try (PreparedStatement ps = conn.prepareStatement(updateSQL.toString())) {
			for (DbTableDataBean colBean : cols) {
				i++;
				switch (colBean.dataType) {
				case STRING:
					ps.setString(i, colBean.columnValue);
					break;
				case LONG:
					ps.setLong(i, Long.parseLong(colBean.columnValue));
					break;
				case INT:
					ps.setInt(i, Integer.parseInt(colBean.columnValue));
					break;
				default:
					break;
				}
			}
			for (DbTableDataBean conditionBean : condition) {
				i++;
				switch (conditionBean.dataType) {
				case STRING:
					ps.setString(i, conditionBean.columnValue);
					break;
				case LONG:
					ps.setLong(i, Long.parseLong(conditionBean.columnValue));
					break;
				case INT:
					ps.setInt(i, Integer.parseInt(conditionBean.columnValue));
					break;
				default:
					break;
				}
			}
			return ps.executeUpdate();
		}
	}
}
