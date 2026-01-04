package com.kusoduck.securities.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kusoduck.securities.entity.FreeCashFlowStatement;
import com.kusoduck.securities.entity.id.StockReportId;

/**
 * t_free_cash_flow_statement 的 Spring Data JPA Repository
 */
@Repository
public interface FreeCashFlowStatementRepository
    extends JpaRepository<FreeCashFlowStatement, StockReportId> {

    // Spring Data JPA 會自動提供基本的 CRUD (Create, Read, Update, Delete) 方法。

    // 您可以根據需求自行添加客製化的查詢方法，例如：

    /**
     * 根據股票代號和年度查詢所有財報資料
     * @param stockCode 股票代號
     * @param reportYear 財報年度
     * @return 匹配的現金流量表清單
     */
    List<FreeCashFlowStatement> findByIdStockCodeAndIdReportYear(String stockCode, Short reportYear);

    // 其他查詢範例：
    // List<CashFlowStatement> findByIdStockCode(String stockCode);
}