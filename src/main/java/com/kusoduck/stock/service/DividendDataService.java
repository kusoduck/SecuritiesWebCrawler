package com.kusoduck.stock.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kusoduck.securities.entity.DividendData;
import com.kusoduck.securities.html.parser.DividendDataParser;
import com.kusoduck.securities.repository.DividendDataRepository;

@Service
public class DividendDataService {

	// Spring 會自動注入 IDividendDataDAO 的實例
	// 這裡使用介面，符合 Spring IoC 的原則
	private final DividendDataRepository repository;

	@Autowired
	public DividendDataService(DividendDataRepository repository) {
		this.repository = repository;
	}

	// 這個方法將執行您的邏輯
	@Transactional // 確保整個方法在一個事務中運行
	public void processDividendData(String dateString) throws IOException {
		System.out.println("Parsing dividend data...");
		List<DividendData> dividendDataPOs = DividendDataParser.parse(dateString, dateString);

		System.out.println("Saving " + dividendDataPOs.size() + " records to the database...");
		repository.saveAll(dividendDataPOs);
		System.out.println("Save completed.");
	}
}
