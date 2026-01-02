package com.kusoduck.securities.entity;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_stock_eps")
public class CumulativeEpsData {

	@EmbeddedId
	private CumulativeEpsDataId id;

	@Column(name = "operating_income")
	private BigDecimal operatingIncome;

	@Column(name = "operating_profit_loss")
	private BigDecimal operatingProfitLoss;

	@Column(name = "non_industry_income")
	private BigDecimal nonIndustryIncome;

	@Column(name = "pre_tax_profit_loss")
	private BigDecimal preTaxProfitLoss;

	@Column(name = "after_tax_profit_loss")
	private BigDecimal afterTaxProfitLoss;

	@Column(name = "eps")
	private BigDecimal eps;

	public CumulativeEpsData() {
	}

	public CumulativeEpsDataId getId() {
		return id;
	}

	public void setId(CumulativeEpsDataId id) {
		this.id = id;
	}

	public BigDecimal getOperatingIncome() {
		return operatingIncome;
	}

	public void setOperatingIncome(BigDecimal operatingIncome) {
		this.operatingIncome = operatingIncome;
	}

	public BigDecimal getOperatingProfitLoss() {
		return operatingProfitLoss;
	}

	public void setOperatingProfitLoss(BigDecimal operatingProfitLoss) {
		this.operatingProfitLoss = operatingProfitLoss;
	}

	public BigDecimal getNonIndustryIncome() {
		return nonIndustryIncome;
	}

	public void setNonIndustryIncome(BigDecimal nonIndustryIncome) {
		this.nonIndustryIncome = nonIndustryIncome;
	}

	public BigDecimal getPreTaxProfitLoss() {
		return preTaxProfitLoss;
	}

	public void setPreTaxProfitLoss(BigDecimal preTaxProfitLoss) {
		this.preTaxProfitLoss = preTaxProfitLoss;
	}

	public BigDecimal getAfterTaxProfitLoss() {
		return afterTaxProfitLoss;
	}

	public void setAfterTaxProfitLoss(BigDecimal afterTaxProfitLoss) {
		this.afterTaxProfitLoss = afterTaxProfitLoss;
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
        CumulativeEpsData that = (CumulativeEpsData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
