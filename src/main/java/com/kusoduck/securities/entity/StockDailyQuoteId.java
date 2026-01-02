package com.kusoduck.securities.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * 複合主鍵類別，對應t_daily_quotes資料表的TRADE_DATE和SECURITY_CODE欄位。
 * 透過使用 @Embeddable 和 @EmbeddedId，可以將複合主鍵的邏輯封裝在一個獨立的類別中，這使得您的實體類別 DailyQuote 更加簡潔和易於管理。
 */
@Embeddable
public class StockDailyQuoteId {

	@Column(name = "TRADE_DATE")
    private LocalDate tradeDate;

    @Column(name = "SECURITY_CODE")
    private String securityCode;

    // 預設建構子
    public StockDailyQuoteId() {
    }

    public StockDailyQuoteId(LocalDate tradeDate, String securityCode) {
        this.tradeDate = tradeDate;
        this.securityCode = securityCode;
    }

    // Getters 和 Setters
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

    // 必須實作 equals() 和 hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) {
			return true;
		}
        if (o == null || getClass() != o.getClass()) {
			return false;
		}
        StockDailyQuoteId that = (StockDailyQuoteId) o;
        return Objects.equals(tradeDate, that.tradeDate) &&
               Objects.equals(securityCode, that.securityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeDate, securityCode);
    }
}
