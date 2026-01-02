package com.kusoduck.securities.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * JPA Entity 類別，對應 `t_stock_ratio` 資料表。
 * 使用 `@EmbeddedId` 來處理複合主鍵。
 */
@Entity
@Table(name = "t_stock_ratio")
public class StockRatio {

    @EmbeddedId
    private StockRatioId id;

    @Column(name = "SECURITY_NAME", length = 45)
    private String securityName;

    @Column(name = "DIVIDEND_YIELD", precision = 4, scale = 2)
    private BigDecimal dividendYield;

    @Column(name = "DIVIDEND_YEAR", length = 4)
    private String dividendYear;

    @Column(name = "PER", precision = 6, scale = 2)
    private BigDecimal per;

    @Column(name = "PBR", precision = 6, scale = 2)
    private BigDecimal pbr;

    @Column(name = "FINANCIAL_YEAR_QUARTER", length = 6)
    private String financialYearQuarter;

    public StockRatioId getId() {
        return id;
    }

    public void setId(StockRatioId id) {
        this.id = id;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public BigDecimal getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(BigDecimal dividendYield) {
        this.dividendYield = dividendYield;
    }

    public String getDividendYear() {
        return dividendYear;
    }

    public void setDividendYear(String dividendYear) {
        this.dividendYear = dividendYear;
    }

    public BigDecimal getPer() {
        return per;
    }

    public void setPer(BigDecimal per) {
        this.per = per;
    }

    public BigDecimal getPbr() {
        return pbr;
    }

    public void setPbr(BigDecimal pbr) {
        this.pbr = pbr;
    }

    public String getFinancialYearQuarter() {
        return financialYearQuarter;
    }

    public void setFinancialYearQuarter(String financialYearQuarter) {
        this.financialYearQuarter = financialYearQuarter;
    }
}
