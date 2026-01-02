package com.kusoduck.securities.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_indice_daily_quotes")
public class IndiceDailyQuote {

    @EmbeddedId
    private IndiceDailyQuoteId id;

    @Column(name = "CLOSING_INDEX", precision = 7, scale = 2)
    private BigDecimal closingIndex;

    @Column(name = "DIRECTION", length = 1)
    private String direction;

    @Column(name = "`CHANGE`", precision = 6, scale = 2)
    private BigDecimal indexChange;

    @Column(name = "CHANGE_PERCENT", precision = 4, scale = 2)
    private BigDecimal changePercent;

    @Column(name = "NOTE", length = 45)
    private String note;

    public IndiceDailyQuote() {
    }

    public IndiceDailyQuoteId getId() {
        return id;
    }

    public void setId(IndiceDailyQuoteId id) {
        this.id = id;
    }

    public BigDecimal getClosingIndex() {
        return closingIndex;
    }

    public void setClosingIndex(BigDecimal closingIndex) {
        this.closingIndex = closingIndex;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BigDecimal getIndexChange() {
        return indexChange;
    }

    public void setIndexChange(BigDecimal indexChange) {
        this.indexChange = indexChange;
    }

    public BigDecimal getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(BigDecimal changePercent) {
        this.changePercent = changePercent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}