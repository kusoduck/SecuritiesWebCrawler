package com.kusoduck.securities.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kusoduck.securities.entity.QuarterlyEpsData;
import com.kusoduck.securities.entity.QuarterlyEpsDataId;


public interface QuarterlyEpsDataRepository extends JpaRepository<QuarterlyEpsData, QuarterlyEpsDataId> {

}
