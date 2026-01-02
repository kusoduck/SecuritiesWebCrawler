package com.kusoduck.stock.service.eps;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kusoduck.securities.entity.CumulativeEpsData;
import com.kusoduck.securities.repository.CumulativeEpsDataRepository;

@Service
public class CumulativeEpsDataService {

	private final CumulativeEpsDataRepository repository;

	@Autowired
	public CumulativeEpsDataService(CumulativeEpsDataRepository theRepository) {
		repository = theRepository;
	}

	public CumulativeEpsData getCumulativeEpsDataBySecurityCodeAndYearAndSeason(String securityCode, int year, int season) {
		return repository.findByIdSecurityCodeAndIdYearAndIdSeason(securityCode, year, season);
	}

    public Optional<List<CumulativeEpsData>> getCumulativeEpsDataByYearAndSeason(int year, int season) {
        return repository.findByIdYearAndIdSeason(year, season);
    }

    @Transactional
    public List<CumulativeEpsData> saveAll(List<CumulativeEpsData> cumulativeEpsList) {
        return repository.saveAll(cumulativeEpsList);
    }

}
