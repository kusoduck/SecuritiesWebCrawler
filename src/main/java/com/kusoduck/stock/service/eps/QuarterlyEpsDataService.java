package com.kusoduck.stock.service.eps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kusoduck.securities.entity.QuarterlyEpsData;
import com.kusoduck.securities.repository.QuarterlyEpsDataRepository;

@Service
public class QuarterlyEpsDataService {

	private final QuarterlyEpsDataRepository repository;

	@Autowired
	public QuarterlyEpsDataService(QuarterlyEpsDataRepository theRepository) {
		repository = theRepository;
	}

	@Transactional
	public void save(QuarterlyEpsData entity) {
		repository.save(entity);
	}
}
