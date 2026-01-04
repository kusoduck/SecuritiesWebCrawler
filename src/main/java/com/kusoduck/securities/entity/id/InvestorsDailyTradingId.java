package com.kusoduck.securities.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//使用 @Embeddable 來定義一個複合主鍵類別
@Embeddable
public class InvestorsDailyTradingId {

	// 定義與資料庫欄位名稱對應的欄位
	@Column(name = "TRADE_DATE")
	private LocalDate tradeDate;

	@Column(name = "SECURITY_CODE", length = 20)
	private String securityCode;

	public InvestorsDailyTradingId() {
	}

	public InvestorsDailyTradingId(LocalDate tradeDate, String securityCode) {
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

	// 複合主鍵類別必須覆寫 equals() 和 hashCode() 方法
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		InvestorsDailyTradingId that = (InvestorsDailyTradingId) o;
		return Objects.equals(tradeDate, that.tradeDate) && Objects.equals(securityCode, that.securityCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(tradeDate, securityCode);
	}
}