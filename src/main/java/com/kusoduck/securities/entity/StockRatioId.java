package com.kusoduck.securities.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * 複合主鍵類別，代表 `t_stock_ratio` 資料表的主鍵 (TRADE_DATE, SECURITY_CODE)。
 */
@Embeddable
public class StockRatioId  {

    @Column(name = "TRADE_DATE", nullable = false)
    private LocalDate tradeDate;

    @Column(name = "SECURITY_CODE", nullable = false, length = 20)
    private String securityCode;

    // 預設建構子是 JPA 規範所必需的
    public StockRatioId() {}

    public StockRatioId(LocalDate tradeDate, String securityCode) {
        this.tradeDate = tradeDate;
        this.securityCode = securityCode;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
			return true;
		}
        if (o == null || getClass() != o.getClass()) {
			return false;
		}
        StockRatioId that = (StockRatioId) o;
        return Objects.equals(tradeDate, that.tradeDate) && Objects.equals(securityCode, that.securityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeDate, securityCode);
    }
}
