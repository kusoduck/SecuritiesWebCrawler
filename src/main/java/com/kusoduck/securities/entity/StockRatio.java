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

/**
 * JPA Entity 類別，對應 `t_stock_ratio` 資料表。
 * 使用 `@EmbeddedId` 來處理複合主鍵。
 */
@Entity
@Table(name = "t_stock_ratio")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockRatio {

    @EmbeddedId
    private StockTradeId id;

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

}
