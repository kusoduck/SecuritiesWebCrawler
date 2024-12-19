package com.kusoduck.stock.po;

import java.math.BigDecimal;

public class StockQuarterlyEpsPO {
	private String stockSymbol;
	private int reportYear;
	private int quarter;
	private BigDecimal eps;

	public StockQuarterlyEpsPO(String stockSymbol, int reportYear, int quarter, BigDecimal eps) {
		this.stockSymbol = stockSymbol;
		this.reportYear = reportYear;
		this.quarter = quarter;
		this.eps = eps;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public int getReportYear() {
		return reportYear;
	}

	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}

	public int getQuarter() {
		return quarter;
	}

	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	public BigDecimal getEps() {
		return eps;
	}

	public void setEps(BigDecimal eps) {
		this.eps = eps;
	}

}
