package com.kusoduck.securities.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kusoduck.securities.entity.IndiceDailyQuote;
import com.kusoduck.securities.entity.IndiceDailyQuoteId;

@Repository
public interface IndiceDailyQuotesRepository extends JpaRepository<IndiceDailyQuote, IndiceDailyQuoteId> {

	// Spring Data JPA 會根據方法名稱自動產生 SQL 查詢
	// 這裡你可以定義更多客製化的查詢方法

	// 範例：根據指數名稱查詢所有報價
	List<IndiceDailyQuote> findByIdIndexName(String indexName);

	// 範例：根據日期和指數名稱查詢單一報價
	// 透過內嵌屬性路徑告訴 Spring Data JPA 這些欄位是在 id 裡：
	IndiceDailyQuote findByIdDateAndIdIndexName(Date date, String indexName);

	// 範例：根據日期範圍查詢某個指數的報價
	List<IndiceDailyQuote> findByIdIndexNameAndIdDateBetween(String indexName, Date startDate, Date endDate);

}
