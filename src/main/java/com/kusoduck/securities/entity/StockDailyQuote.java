package com.kusoduck.securities.entity;

import java.math.BigDecimal;

import com.kusoduck.securities.entity.id.StockTradeId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA實體類別，對應t_daily_quotes資料表。
 */
@Entity
@Table(name = "t_daily_quotes")
@Getter
@Setter
public class StockDailyQuote {

    @EmbeddedId
    private StockTradeId id;

    @Column(name = "SECURITY_NAME")
    private String securityName;

    @Column(name = "TRADE_VOLUME")
    private Long tradeVolume;

    @Column(name = "TRANSACTION")
    private Long transaction;

    @Column(name = "TRADE_VALUE")
    private Long tradeValue;

    @Column(name = "OPENING_PRICE")
    private BigDecimal openingPrice;

    @Column(name = "HIGHEST_PRICE")
    private BigDecimal highestPrice;

    @Column(name = "LOWEST_PRICE")
    private BigDecimal lowestPrice;

    @Column(name = "CLOSING_PRICE")
    private BigDecimal closingPrice;

    @Column(name = "DIRECTION")
    private String direction;

    @Column(name = "`PRICE_DIFF`")
    private BigDecimal priceDiff;

    @Column(name = "LAST_BEST_BID_PRICE")
    private BigDecimal lastBestBidPrice;

    @Column(name = "LAST_BEST_BID_VOLUME")
    private Long lastBestBidVolume;

    @Column(name = "LAST_BEST_ASK_PRICE")
    private BigDecimal lastBestAskPrice;

    @Column(name = "LAST_BEST_ASK_VOLUME")
    private Long lastBestAskVolume;

    @Column(name = "PRICE_EARNING_RATIO")
    private BigDecimal priceEarningRatio;
}
