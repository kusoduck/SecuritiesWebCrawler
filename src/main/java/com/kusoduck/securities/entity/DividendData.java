package com.kusoduck.securities.entity;

import java.math.BigDecimal;

import com.kusoduck.securities.entity.id.DividendDataId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 除權息資料表 (t_dividend_data) 的 POJO 類別。
 * This class represents the dividend data from the t_dividend_data table.
 */
@Entity
@Table(name = "t_dividend_data")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DividendData {

    @EmbeddedId
    private DividendDataId id;
    
    /**
     * 除權息前收盤價.
     */
    @Column(name = "closing_price_before", nullable = false, precision = 10, scale = 2)
    private BigDecimal closingPriceBefore;

    /**
     * 除權息參考價.
     */
    @Column(name = "reference_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal referencePrice;

    /**
     * 權值+息值.
     */
    @Column(name = "dividend_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal dividendValue;

}
