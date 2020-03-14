package stock.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradeDateDAO {
	private static final String SELECT = "SELECT 日期  FROM securities.tradedate where 日期  between ? and ?;";

	private TradeDateDAO() {

	}

	public static List<String> select(Connection conn, String startDate, String endDate) {
		try (PreparedStatement stmt = conn.prepareStatement(SELECT)) {
			stmt.setString(1, startDate);
			stmt.setString(2, endDate);
			try (ResultSet rs = stmt.executeQuery()) {
				List<String> dateList = new ArrayList<>();
				while (rs.next()) {
					dateList.add(rs.getString(1));
				}
				return dateList;
			}
		} catch (SQLException ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
}
