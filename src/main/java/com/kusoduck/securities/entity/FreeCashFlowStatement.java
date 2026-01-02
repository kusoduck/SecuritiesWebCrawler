package com.kusoduck.securities.entity;

import com.kusoduck.securities.entity.id.FreeCashFlowStatementId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * t_cash_flow_statement 資料表對應的 JPA Entity
 */
@Entity
@Table(name = "t_free_cash_flow_statement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FreeCashFlowStatement {
    // 嵌入複合主鍵
    @EmbeddedId
    private FreeCashFlowStatementId id;

    @Column(name = "operating_cash_flow")
    private Long operatingCashFlow; // 對應 BIGINT

    @Column(name = "investing_cash_flow")
    private Long investingCashFlow; // 對應 BIGINT

    @Column(name = "financing_cash_flow")
    private Long financingCashFlow; // 對應 BIGINT

    @Column(name = "fx_rate_impact")
    private Long fxRateImpact; // 對應 BIGINT

    @Column(name = "cash_increase_decrease")
    private Long cashIncreaseDecrease; // 對應 BIGINT

    @Column(name = "beginning_cash_balance")
    private Long beginningCashBalance; // 對應 BIGINT

    @Column(name = "ending_cash_balance")
    private Long endingCashBalance; // 對應 BIGINT

}