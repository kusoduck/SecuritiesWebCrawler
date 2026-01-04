package com.kusoduck.securities.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kusoduck.securities.entity.InvestorsDailyTrading;
import com.kusoduck.securities.entity.id.StockTradeId;

@Repository
public interface InvestorDailyTradingRepository extends JpaRepository<InvestorsDailyTrading, StockTradeId>{

    Optional<InvestorsDailyTrading> findFirstByOrderByIdTradeDateDesc();
}
