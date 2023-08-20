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
		this.securityCode = rs.getString("SECURITY_CODE");
		this.year = rs.getDate("YEAR").toLocalDate().getYear();
		this.season = rs.getInt("SEASON");
	}

	public String getSecurityCode() {
		return this.securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSeason() {
		return this.season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public BigDecimal getOperatingIncome() {
		return this.operatingIncome;
	}

	public void setOperatingIncome(BigDecimal operatingIncome) {
		this.operatingIncome = operatingIncome;
	}

	public BigDecimal getOperatingProfitLoss() {
		return this.operatingProfitLoss;
	}

	public void setOperatingProfitLoss(BigDecimal operatingProfitLoss) {
		this.operatingProfitLoss = operatingProfitLoss;
	}

	public BigDecimal getNonIndustryIncome() {
		return this.nonIndustryIncome;
	}

	public void setNonIndustryIncome(BigDecimal nonIndustryIncome) {
		this.nonIndustryIncome = nonIndustryIncome;
	}

	public BigDecimal getPreTaxProfitLoss() {
		return this.preTaxProfitLoss;
	}

	public void setPreTaxProfitLoss(BigDecimal preTaxProfitLoss) {
		this.preTaxProfitLoss = preTaxProfitLoss;
	}

	public BigDecimal getAfterTaxProfitLoss() {
		return this.afterTaxProfitLoss;
	}

	public void setAfterTaxProfitLoss(BigDecimal afterTaxProfitLoss) {
		this.afterTaxProfitLoss = afterTaxProfitLoss;
	}

	public BigDecimal getEps() {
		return this.eps;
	}

	public void setEps(BigDecimal eps) {
		this.eps = eps;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.season, this.securityCode, this.year);
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
		return this.season == other.season && Objects.equals(this.securityCode, other.securityCode) && this.year == other.year;
	}

}
