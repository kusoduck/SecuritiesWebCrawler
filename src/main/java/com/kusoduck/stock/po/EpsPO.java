package com.kusoduck.stock.po;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class EpsPO {
	String securityCode;
	int year;
	int season;
	BigDecimal operatingIncome;
	BigDecimal operatingProfitLoss;
	BigDecimal nonIndustryIncome;
	BigDecimal preTaxProfitLoss;
	BigDecimal afterTaxProfitLoss;
	BigDecimal eps;

	public EpsPO() {
	}

	public EpsPO(ResultSet rs) throws SQLException {
		securityCode = rs.getString("SECURITY_CODE");
		year = rs.getDate("YEAR").toLocalDate().getYear();
		season = rs.getInt("SEASON");
		eps = rs.getBigDecimal("EPS");
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public BigDecimal getOperatingIncome() {
		return operatingIncome;
	}

	public void setOperatingIncome(BigDecimal operatingIncome) {
		this.operatingIncome = operatingIncome;
	}

	public BigDecimal getOperatingProfitLoss() {
		return operatingProfitLoss;
	}

	public void setOperatingProfitLoss(BigDecimal operatingProfitLoss) {
		this.operatingProfitLoss = operatingProfitLoss;
	}

	public BigDecimal getNonIndustryIncome() {
		return nonIndustryIncome;
	}

	public void setNonIndustryIncome(BigDecimal nonIndustryIncome) {
		this.nonIndustryIncome = nonIndustryIncome;
	}

	public BigDecimal getPreTaxProfitLoss() {
		return preTaxProfitLoss;
	}

	public void setPreTaxProfitLoss(BigDecimal preTaxProfitLoss) {
		this.preTaxProfitLoss = preTaxProfitLoss;
	}

	public BigDecimal getAfterTaxProfitLoss() {
		return afterTaxProfitLoss;
	}

	public void setAfterTaxProfitLoss(BigDecimal afterTaxProfitLoss) {
		this.afterTaxProfitLoss = afterTaxProfitLoss;
	}

	public BigDecimal getEps() {
		return eps;
	}

	public void setEps(BigDecimal eps) {
		this.eps = eps;
	}

	@Override
	public int hashCode() {
		return Objects.hash(season, securityCode, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		EpsPO other = (EpsPO) obj;
		return season == other.season && Objects.equals(securityCode, other.securityCode) && year == other.year;
	}

}
