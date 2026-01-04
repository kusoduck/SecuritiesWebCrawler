package com.kusoduck.securities.entity;

import java.math.BigDecimal;

import com.kusoduck.securities.entity.id.IndiceDailyQuoteId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_indice_daily_quotes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IndiceDailyQuote {

    @EmbeddedId
    private IndiceDailyQuoteId id;

    @Column(name = "CLOSING_INDEX", precision = 7, scale = 2)
    private BigDecimal closingIndex;

    @Column(name = "DIRECTION", length = 1)
    private String direction;

    @Column(name = "INDEX_DIFF", precision = 6, scale = 2)
    private BigDecimal indexDiff;

    @Column(name = "CHANGE_PERCENT", precision = 4, scale = 2)
    private BigDecimal changePercent;

    @Column(name = "NOTE", length = 45)
    private String note;

}