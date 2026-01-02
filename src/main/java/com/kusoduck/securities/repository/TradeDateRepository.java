package com.kusoduck.securities.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kusoduck.securities.entity.TradeDate;

@Repository
public interface TradeDateRepository extends JpaRepository<TradeDate, LocalDate> {

    /**
     * 查詢最新的交易日期。
     * Spring Data JPA 會自動生成 SQL:
     * SELECT * FROM v_trade_date ORDER BY trade_date DESC LIMIT 1
     */
    Optional<TradeDate> findFirstByOrderByTradeDateDesc();
}
