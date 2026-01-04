package com.kusoduck.securities.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kusoduck.securities.entity.CumulativeEpsData;
import com.kusoduck.securities.entity.id.StockReportId;

@Repository
public interface CumulativeEpsDataRepository extends JpaRepository<CumulativeEpsData, StockReportId>{

	Optional<List<CumulativeEpsData>> findByIdYearAndIdSeason(int year,int season);

	CumulativeEpsData findByIdSecurityCodeAndIdYearAndIdSeason(String securityCode, int year, int season);
}
