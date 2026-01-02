package com.kusoduck.securities.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * JPA實體類別，對應t_daily_quotes資料表。
 */
@Entity
@Table(name = "t_daily_quotes")
public class StockDailyQuote {

    @EmbeddedId
    private StockDailyQuoteId id;

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

    @Column(name = "`CHANGE`")
    private BigDecimal priceChange;

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

    public StockDailyQuoteId getId() {
        return id;
    }

    public void setId(StockDailyQuoteId id) {
        this.id = id;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public Long getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(Long tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    public Long getTransaction() {
        return transaction;
    }

    public void setTransaction(Long transaction) {
        this.transaction = transaction;
    }

    public Long getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(Long tradeValue) {
        this.tradeValue = tradeValue;
    }

    public BigDecimal getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(BigDecimal openingPrice) {
        this.openingPrice = openingPrice;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BigDecimal getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(BigDecimal priceChange) {
        this.priceChange = priceChange;
    }

    public BigDecimal getLastBestBidPrice() {
        return lastBestBidPrice;
    }

    public void setLastBestBidPrice(BigDecimal lastBestBidPrice) {
        this.lastBestBidPrice = lastBestBidPrice;
    }

    public Long getLastBestBidVolume() {
        return lastBestBidVolume;
    }

    public void setLastBestBidVolume(Long lastBestBidVolume) {
        this.lastBestBidVolume = lastBestBidVolume;
    }

    public BigDecimal getLastBestAskPrice() {
        return lastBestAskPrice;
    }

    public void setLastBestAskPrice(BigDecimal lastBestAskPrice) {
        this.lastBestAskPrice = lastBestAskPrice;
    }

    public Long getLastBestAskVolume() {
        return lastBestAskVolume;
    }

    public void setLastBestAskVolume(Long lastBestAskVolume) {
        this.lastBestAskVolume = lastBestAskVolume;
    }

    public BigDecimal getPriceEarningRatio() {
        return priceEarningRatio;
    }

    public void setPriceEarningRatio(BigDecimal priceEarningRatio) {
        this.priceEarningRatio = priceEarningRatio;
    }
}
