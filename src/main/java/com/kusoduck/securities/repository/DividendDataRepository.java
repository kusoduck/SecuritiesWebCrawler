package com.kusoduck.securities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kusoduck.securities.entity.DividendData;
import com.kusoduck.securities.entity.id.DividendDataId;

@Repository
public interface DividendDataRepository extends JpaRepository<DividendData, DividendDataId> {

}
