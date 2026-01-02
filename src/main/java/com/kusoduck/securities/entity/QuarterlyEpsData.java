package com.kusoduck.securities.entity;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 代表 t_stock_quarterly_eps 表格的 JPA 實體類別。
 * 使用 @EmbeddedId 來處理複合主鍵。
 */
@Entity
@Table(name = "t_stock_quarterly_eps")
public class QuarterlyEpsData {
    @EmbeddedId
    private QuarterlyEpsDataId id;

    @Column(name = "eps", precision = 5, scale = 2, nullable = false)
    private BigDecimal eps;

    public QuarterlyEpsData() {

    }

    public QuarterlyEpsData(QuarterlyEpsDataId id, BigDecimal eps) {
        this.id = id;
        this.eps = eps;
    }

    public QuarterlyEpsDataId getId() {
        return id;
    }

    public void setId(QuarterlyEpsDataId id) {
        this.id = id;
    }

    public BigDecimal getEps() {
        return eps;
    }

    public void setEps(BigDecimal eps) {
        this.eps = eps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
			return true;
		}
        if (o == null || getClass() != o.getClass()) {
			return false;
		}
        QuarterlyEpsData that = (QuarterlyEpsData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}