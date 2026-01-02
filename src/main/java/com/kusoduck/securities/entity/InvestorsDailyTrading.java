package com.kusoduck.securities.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//使用 @Entity 標註此類別為 JPA 實體
@Entity
//使用 @Table 指定對應的資料表名稱
@Table(name = "t_investors_daily_trading")
public class InvestorsDailyTrading {

	// 使用 @EmbeddedId 標註複合主鍵
	@EmbeddedId
	private InvestorsDailyTradingId id;

	@Column(name = "SECURITY_NAME", length = 45)
	private String securityName;

	@Column(name = "FOREIGN_INVESTORS_TOTAL_BUY")
	private BigDecimal foreignInvestorsTotalBuy;

	@Column(name = "FOREIGN_INVESTORS_TOTAL_SELL")
	private BigDecimal foreignInvestorsTotalSell;

	@Column(name = "FOREIGN_INVESTORS_DIFFERENCE")
	private BigDecimal foreignInvestorsDifference;

	@Column(name = "FOREIGN_DEALERS_TOTAL_BUY")
	private BigDecimal foreignDealersTotalBuy;

	@Column(name = "FOREIGN_DEALERS_TOTAL_SELL")
	private BigDecimal foreignDealersTotalSell;

	@Column(name = "FOREIGN_DEALERS_DIFFERENCE")
	private BigDecimal foreignDealersDifference;

	@Column(name = "SECURITIES_INVESTMENT_TRUST_COMPANIES_TOTAL_BUY")
	private BigDecimal securitiesInvestmentTrustCompaniesTotalBuy;

	@Column(name = "SECURITIES_INVESTMENT_TRUST_COMPANIES_TOTAL_SELL")
	private BigDecimal securitiesInvestmentTrustCompaniesTotalSell;

	@Column(name = "SECURITIES_INVESTMENT_TRUST_COMPANIES_TOTAL_DIFFERENCE")
	private BigDecimal securitiesInvestmentTrustCompaniesTotalDifference;

	@Column(name = "DEALERS_DIFFERENCE")
	private BigDecimal dealersDifference;

	@Column(name = "DEALERS_PROPRIETARY_TOTAL_BUY")
	private BigDecimal dealersProprietaryTotalBuy;

	@Column(name = "DEALERS_PROPRIETARY_TOTAL_SELL")
	private BigDecimal dealersProprietaryTotalSell;

	@Column(name = "DEALERS_PROPRIETARY_DIFFERENCE")
	private BigDecimal dealersProprietaryDifference;

	@Column(name = "DEALERS_HEDGE_TOTAL_BUY")
	private BigDecimal dealersHedgeTotalBuy;

	@Column(name = "DEALERS_HEDGE_TOTAL_SELL")
	private BigDecimal dealersHedgeTotalSell;

	@Column(name = "DEALERS_HEDGE_DIFFERENCE")
	private BigDecimal dealersHedgeDifference;

	@Column(name = "TOTAL_DIFFERENCE")
	private BigDecimal totalDifference;

	public InvestorsDailyTradingId getId() {
		return id;
	}

	public void setId(InvestorsDailyTradingId id) {
		this.id = id;
	}

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}

	public BigDecimal getForeignInvestorsTotalBuy() {
		return foreignInvestorsTotalBuy;
	}

	public void setForeignInvestorsTotalBuy(BigDecimal foreignInvestorsTotalBuy) {
		this.foreignInvestorsTotalBuy = foreignInvestorsTotalBuy;
	}

	public BigDecimal getForeignInvestorsTotalSell() {
		return foreignInvestorsTotalSell;
	}

	public void setForeignInvestorsTotalSell(BigDecimal foreignInvestorsTotalSell) {
		this.foreignInvestorsTotalSell = foreignInvestorsTotalSell;
	}

	public BigDecimal getForeignInvestorsDifference() {
		return foreignInvestorsDifference;
	}

	public void setForeignInvestorsDifference(BigDecimal foreignInvestorsDifference) {
		this.foreignInvestorsDifference = foreignInvestorsDifference;
	}

	public BigDecimal getForeignDealersTotalBuy() {
		return foreignDealersTotalBuy;
	}

	public void setForeignDealersTotalBuy(BigDecimal foreignDealersTotalBuy) {
		this.foreignDealersTotalBuy = foreignDealersTotalBuy;
	}

	public BigDecimal getForeignDealersTotalSell() {
		return foreignDealersTotalSell;
	}

	public void setForeignDealersTotalSell(BigDecimal foreignDealersTotalSell) {
		this.foreignDealersTotalSell = foreignDealersTotalSell;
	}

	public BigDecimal getForeignDealersDifference() {
		return foreignDealersDifference;
	}

	public void setForeignDealersDifference(BigDecimal foreignDealersDifference) {
		this.foreignDealersDifference = foreignDealersDifference;
	}

	public BigDecimal getSecuritiesInvestmentTrustCompaniesTotalBuy() {
		return securitiesInvestmentTrustCompaniesTotalBuy;
	}

	public void setSecuritiesInvestmentTrustCompaniesTotalBuy(BigDecimal securitiesInvestmentTrustCompaniesTotalBuy) {
		this.securitiesInvestmentTrustCompaniesTotalBuy = securitiesInvestmentTrustCompaniesTotalBuy;
	}

	public BigDecimal getSecuritiesInvestmentTrustCompaniesTotalSell() {
		return securitiesInvestmentTrustCompaniesTotalSell;
	}

	public void setSecuritiesInvestmentTrustCompaniesTotalSell(BigDecimal securitiesInvestmentTrustCompaniesTotalSell) {
		this.securitiesInvestmentTrustCompaniesTotalSell = securitiesInvestmentTrustCompaniesTotalSell;
	}

	public BigDecimal getSecuritiesInvestmentTrustCompaniesTotalDifference() {
		return securitiesInvestmentTrustCompaniesTotalDifference;
	}

	public void setSecuritiesInvestmentTrustCompaniesTotalDifference(BigDecimal securitiesInvestmentTrustCompaniesTotalDifference) {
		this.securitiesInvestmentTrustCompaniesTotalDifference = securitiesInvestmentTrustCompaniesTotalDifference;
	}

	public BigDecimal getDealersDifference() {
		return dealersDifference;
	}

	public void setDealersDifference(BigDecimal dealersDifference) {
		this.dealersDifference = dealersDifference;
	}

	public BigDecimal getDealersProprietaryTotalBuy() {
		return dealersProprietaryTotalBuy;
	}

	public void setDealersProprietaryTotalBuy(BigDecimal dealersProprietaryTotalBuy) {
		this.dealersProprietaryTotalBuy = dealersProprietaryTotalBuy;
	}

	public BigDecimal getDealersProprietaryTotalSell() {
		return dealersProprietaryTotalSell;
	}

	public void setDealersProprietaryTotalSell(BigDecimal dealersProprietaryTotalSell) {
		this.dealersProprietaryTotalSell = dealersProprietaryTotalSell;
	}

	public BigDecimal getDealersProprietaryDifference() {
		return dealersProprietaryDifference;
	}

	public void setDealersProprietaryDifference(BigDecimal dealersProprietaryDifference) {
		this.dealersProprietaryDifference = dealersProprietaryDifference;
	}

	public BigDecimal getDealersHedgeTotalBuy() {
		return dealersHedgeTotalBuy;
	}

	public void setDealersHedgeTotalBuy(BigDecimal dealersHedgeTotalBuy) {
		this.dealersHedgeTotalBuy = dealersHedgeTotalBuy;
	}

	public BigDecimal getDealersHedgeTotalSell() {
		return dealersHedgeTotalSell;
	}

	public void setDealersHedgeTotalSell(BigDecimal dealersHedgeTotalSell) {
		this.dealersHedgeTotalSell = dealersHedgeTotalSell;
	}

	public BigDecimal getDealersHedgeDifference() {
		return dealersHedgeDifference;
	}

	public void setDealersHedgeDifference(BigDecimal dealersHedgeDifference) {
		this.dealersHedgeDifference = dealersHedgeDifference;
	}

	public BigDecimal getTotalDifference() {
		return totalDifference;
	}

	public void setTotalDifference(BigDecimal totalDifference) {
		this.totalDifference = totalDifference;
	}
}