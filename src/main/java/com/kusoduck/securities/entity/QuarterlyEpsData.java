package com.kusoduck.securities.entity;

import java.math.BigDecimal;

import com.kusoduck.securities.entity.id.StockReportId;

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
 * 代表 t_stock_quarterly_eps 表格的 JPA 實體類別。
 * 使用 @EmbeddedId 來處理複合主鍵。
 */
@Entity
@Table(name = "t_stock_quarterly_eps")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuarterlyEpsData {
    @EmbeddedId
    private StockReportId id;

    @Column(name = "eps", precision = 5, scale = 2, nullable = false)
    private BigDecimal eps;

}