package com.kusoduck.securities.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class QuarterlyEpsDataId {
    // JPA 要求所有 @Embeddable 類別都必須有一個無參數的建構子
    public QuarterlyEpsDataId() {}

    /**
     * 帶有所有欄位的建構子。
     * @param stockSymbol 股票代號
     * @param reportYear 報告年份
     * @param quarter 季度
     */
    public QuarterlyEpsDataId(String stockSymbol, int reportYear, int quarter) {
        this.stockSymbol = stockSymbol;
        this.reportYear = reportYear;
        this.quarter = quarter;
    }

    @Column(name = "stock_symbol", length = 45, nullable = false)
    private String stockSymbol;

    @Column(name = "report_year", nullable = false)
    private int reportYear;

    @Column(name = "quarter", nullable = false)
    private int quarter;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
			return true;
		}
        if (o == null || getClass() != o.getClass()) {
			return false;
		}
        QuarterlyEpsDataId that = (QuarterlyEpsDataId) o;
        return reportYear == that.reportYear &&
               quarter == that.quarter &&
               Objects.equals(stockSymbol, that.stockSymbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockSymbol, reportYear, quarter);
    }
}
