package com.kusoduck.securities.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kusoduck.securities.entity.StockRatio;
import com.kusoduck.securities.entity.id.StockTradeId;

@Repository
public interface StockRatioRepository extends JpaRepository<StockRatio, StockTradeId>{

    Optional<StockRatio> findFirstByOrderByIdTradeDateDesc();
}
