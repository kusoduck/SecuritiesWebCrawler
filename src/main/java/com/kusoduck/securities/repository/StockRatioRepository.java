package com.kusoduck.securities.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kusoduck.securities.entity.StockRatio;
import com.kusoduck.securities.entity.StockRatioId;

@Repository
public interface StockRatioRepository extends JpaRepository<StockRatio, StockRatioId>{

    Optional<StockRatio> findFirstByOrderByIdTradeDateDesc();
}
