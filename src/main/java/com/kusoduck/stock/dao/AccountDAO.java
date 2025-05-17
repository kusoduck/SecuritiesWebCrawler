package com.kusoduck.stock.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kusoduck.stock.po.AccountPO;

public class AccountDAO {
	private static Logger logger = LoggerFactory.getLogger(AccountDAO.class);

	private AccountDAO() {

	}

	public static AccountPO find(Connection conn, String name) {
		String sql = "select * from account where name=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			int i = 1;
			ps.setString(i++, name);
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new AccountPO(rs);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}
