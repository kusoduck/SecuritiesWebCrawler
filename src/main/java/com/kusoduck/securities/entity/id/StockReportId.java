package com.kusoduck.securities.entity.id;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 現金流量表複合主鍵類別
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StockReportId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "stock_code", nullable = false, length = 10)
    private String stockCode; // 對應 CHAR(10)

    @Column(name = "report_year", nullable = false)
    private Short reportYear; // 對應 SMALLINT

    @Column(name = "report_quarter", nullable = false)
    private Byte reportQuarter; // 對應 TINYINT
}