package com.kusoduck.securities.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 除權息資料表 (t_dividend_data) 的 POJO 類別。
 * This class represents the dividend data from the t_dividend_data table.
 */
@Entity
@Table(name = "t_dividend_data")
public class DividendData {

    /**
     * 主鍵 ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 除權息日期.
     */
    @Column(name = "ex_dividend_date", nullable = false)
    private LocalDate exDividendDate;

    /**
     * 股票代號.
     */
    @Column(name = "stock_code", nullable = false, length = 10)
    private String stockCode;

    /**
     * 除權息前收盤價.
     */
    @Column(name = "closing_price_before", nullable = false, precision = 10, scale = 2)
    private BigDecimal closingPriceBefore;

    /**
     * 除權息參考價.
     */
    @Column(name = "reference_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal referencePrice;

    /**
     * 權值+息值.
     */
    @Column(name = "dividend_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal dividendValue;

    // Constructors
    public DividendData() {
    }

    public DividendData(LocalDate exDividendDate, String stockCode, BigDecimal closingPriceBefore, BigDecimal referencePrice, BigDecimal dividendValue) {
        this.exDividendDate = exDividendDate;
        this.stockCode = stockCode;
        this.closingPriceBefore = closingPriceBefore;
        this.referencePrice = referencePrice;
        this.dividendValue = dividendValue;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getExDividendDate() {
        return exDividendDate;
    }

    public void setExDividendDate(LocalDate exDividendDate) {
        this.exDividendDate = exDividendDate;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public BigDecimal getClosingPriceBefore() {
        return closingPriceBefore;
    }

    public void setClosingPriceBefore(BigDecimal closingPriceBefore) {
        this.closingPriceBefore = closingPriceBefore;
    }

    public BigDecimal getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(BigDecimal referencePrice) {
        this.referencePrice = referencePrice;
    }

    public BigDecimal getDividendValue() {
        return dividendValue;
    }

    public void setDividendValue(BigDecimal dividendValue) {
        this.dividendValue = dividendValue;
    }

    @Override
    public String toString() {
        return "DividendData{" +
                "id=" + id +
                ", exDividendDate=" + exDividendDate +
                ", stockCode='" + stockCode + '\'' +
                ", closingPriceBefore=" + closingPriceBefore +
                ", referencePrice=" + referencePrice +
                ", dividendValue=" + dividendValue +
                '}';
    }
}
