package com.kusoduck.securities.entity.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
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
public class FreeCashFlowStatementId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "stock_code", nullable = false, length = 4)
    private String stockCode; // 對應 CHAR(4)

    @Column(name = "report_year", nullable = false)
    private Short reportYear; // 對應 SMALLINT

    @Column(name = "report_quarter", nullable = false)
    private Byte reportQuarter; // 對應 TINYINT

    // 必須實作 equals() 和 hashCode() 方法
    @Override
    public boolean equals(Object o) {
        if (this == o) {
			return true;
		}
        if (o == null || getClass() != o.getClass()) {
			return false;
		}
        FreeCashFlowStatementId that = (FreeCashFlowStatementId) o;
        return Objects.equals(stockCode, that.stockCode) &&
               Objects.equals(reportYear, that.reportYear) &&
               Objects.equals(reportQuarter, that.reportQuarter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockCode, reportYear, reportQuarter);
    }


}