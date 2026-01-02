package com.kusoduck.stock.service.eps;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kusoduck.securities.entity.CumulativeEpsData;
import com.kusoduck.securities.entity.QuarterlyEpsData;
import com.kusoduck.securities.entity.QuarterlyEpsDataId;
import com.kusoduck.securities.html.MopsRedirector;
import com.kusoduck.securities.html.parser.EpsParser;
import com.kusoduck.utils.CalcuateQuarterUtils;

@Service
public class EpsService {
	private static final Logger logger = LoggerFactory.getLogger(EpsService.class);
	private static final String apiName = "ajax_t163sb04";

	@Autowired
	private MopsRedirector mopsRedirector;
	@Autowired
	private EpsParser epsParser;
	@Autowired
	private CumulativeEpsDataService cumulativeEpsService;
	@Autowired
	private QuarterlyEpsDataService quarterlyEpsDataService;

	public void crawlData(LocalDate current) throws SQLException {
		Pair<Integer, Integer> previousRocYearQuarter = CalcuateQuarterUtils.calcPreviousYearQuarter(current);

		int year = previousRocYearQuarter.getLeft();
		int quarter = previousRocYearQuarter.getRight();
		int rocYear = CalcuateQuarterUtils.toRocYear(year);

		String url = mopsRedirector.getRedirectUrl(apiName, rocYear, quarter);
		logger.debug("EPS Parser - url : {}",url);

		List<CumulativeEpsData> cumulativeEpsDataList = epsParser.parse(url,rocYear,quarter);
		List<CumulativeEpsData> dbCumulativeEpsDataList = cumulativeEpsService.getCumulativeEpsDataByYearAndSeason(year, quarter).get();
		List<CumulativeEpsData> notExistCumulativeEpsDataList = (List<CumulativeEpsData>) CollectionUtils.removeAll(cumulativeEpsDataList, dbCumulativeEpsDataList);
		cumulativeEpsService.saveAll(notExistCumulativeEpsDataList);

		// 計算出每季的EPS
		if (quarter > 1) {
			for (CumulativeEpsData cumulativeEps : notExistCumulativeEpsDataList) {
				// 取得上一季的前一季，用來計算季與季的差額
				Pair<Integer, Integer> lastYearQuarterPair = CalcuateQuarterUtils.calcPreviousQuarter(year, quarter);
				String stockSymbol = cumulativeEps.getId().getSecurityCode();
				CumulativeEpsData lastCumulativeEpsData = cumulativeEpsService.getCumulativeEpsDataBySecurityCodeAndYearAndSeason(stockSymbol,
						lastYearQuarterPair.getLeft(), lastYearQuarterPair.getRight());
				if (lastCumulativeEpsData != null) {
					BigDecimal quarterlyEps = cumulativeEps.getEps().subtract(lastCumulativeEpsData.getEps());
					QuarterlyEpsDataId quarterlyEpsDataId = new QuarterlyEpsDataId(stockSymbol, year, quarter);
					QuarterlyEpsData quarterlyEpsData = new QuarterlyEpsData(quarterlyEpsDataId, quarterlyEps);
					quarterlyEpsDataService.save(quarterlyEpsData);
				}
			}
		}else {
			for (CumulativeEpsData cumulativeEps : notExistCumulativeEpsDataList) {
					QuarterlyEpsDataId quarterlyEpsDataId = new QuarterlyEpsDataId(cumulativeEps.getId().getSecurityCode(), year, quarter);
					QuarterlyEpsData quarterlyEpsData = new QuarterlyEpsData(quarterlyEpsDataId, cumulativeEps.getEps());
					quarterlyEpsDataService.save(quarterlyEpsData);
			}
		}
	}
}
