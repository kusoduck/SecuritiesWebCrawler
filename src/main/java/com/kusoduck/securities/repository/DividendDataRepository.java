package com.kusoduck.securities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kusoduck.securities.entity.DividendData;

@Repository
public interface DividendDataRepository extends JpaRepository<DividendData, Integer> {

}
