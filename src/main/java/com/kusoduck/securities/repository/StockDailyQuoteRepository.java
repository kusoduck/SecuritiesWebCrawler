package com.kusoduck.securities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kusoduck.securities.entity.StockDailyQuote;
import com.kusoduck.securities.entity.StockDailyQuoteId;

/**
 * Spring Data JPA Repository 介面，用於操作 DailyQuote 實體。
 */
@Repository
public interface StockDailyQuoteRepository extends JpaRepository<StockDailyQuote, StockDailyQuoteId> {
    // Spring Data JPA 會自動提供基本 CRUD (Create, Read, Update, Delete) 方法。
    // 您可以在此處新增客製化的查詢方法，例如：
    // List<DailyQuote> findById_SecurityCode(String securityCode);
}
