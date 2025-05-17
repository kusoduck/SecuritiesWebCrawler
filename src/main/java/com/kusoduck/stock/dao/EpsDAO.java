package com.kusoduck.stock.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.kusoduck.stock.po.EpsPO;

public class EpsDAO {

	public static List<EpsPO> find(Connection conn, int year, int season) {
		List<EpsPO> epsPOs = new ArrayList<>();
		String sql = "select * from t_stock_eps where YEAR=? and SEASON=?";
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			int i = 1;
			ps.setInt(i++, year);
			ps.setInt(i++, season);

			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					EpsPO po = new EpsPO(rs);
					epsPOs.add(po);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return epsPOs;
	}

	public static EpsPO find(Connection conn, int year, int season, String securityCode) {
		String sql = "select * from t_stock_eps where YEAR=? and SEASON=? and SECURITY_CODE=?";
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			int i = 1;
			ps.setInt(i++, year);
			ps.setInt(i++, season);
			ps.setString(i, securityCode);

			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					return new EpsPO(rs);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void insert(Connection conn, List<EpsPO> epsPOs) {
		String sql = "insert into t_stock_eps(SECURITY_CODE, YEAR, SEASON, OPERATING_INCOME, OPERATING_PROFT_LOSS, NON_INDUSTRY_INCOME, PRE_TAX_PROFIT_LOSS, AFTER_TAX_PROFIT_LOSS, EPS) values(?,?,?,?,?,?,?,?,?);";
		QueryRunner queryRunner = new QueryRunner();

		epsPOs.stream().forEach(t -> {
			try {
				queryRunner.update(conn, sql, t.getSecurityCode(), t.getYear(), t.getSeason(), t.getOperatingIncome(),
						t.getOperatingProfitLoss(), t.getNonIndustryIncome(), t.getPreTaxProfitLoss(),
						t.getAfterTaxProfitLoss(), t.getEps());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}
}
