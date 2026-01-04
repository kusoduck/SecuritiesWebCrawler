package com.kusoduck.securities.entity;

import java.math.BigDecimal;

import com.kusoduck.securities.entity.id.StockTradeId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//使用 @Entity 標註此類別為 JPA 實體
@Entity
//使用 @Table 指定對應的資料表名稱
@Table(name = "t_investors_daily_trading")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvestorsDailyTrading {

	// 使用 @EmbeddedId 標註複合主鍵
	@EmbeddedId
	private StockTradeId id;

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

}