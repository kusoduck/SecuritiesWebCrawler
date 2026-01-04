package com.kusoduck.securities.html.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.kusoduck.securities.entity.FreeCashFlowStatement;
import com.kusoduck.securities.entity.id.StockReportId;
import com.kusoduck.stock.constant.FreeCashFlowStatementHeader;
import com.kusoduck.utils.NumberHandleUtils;
import com.kusoduck.utils.ParseHtmlUtils;

@Service
public class FreeCashFlowStatementParser extends HtmlParser<FreeCashFlowStatement, FreeCashFlowStatementHeader> {

	@Override
	public List<FreeCashFlowStatement> parse(String url, Object... context) {
		List<FreeCashFlowStatement> freeCashFlowStatements = new ArrayList<>();
		try {
			ParseHtmlUtils.getDocument(url);
			Document doc = ParseHtmlUtils.getDocument(url);
			Elements tables = doc.select("table.hasBorder");
			for (Element table : tables) {
				Map<Integer, FreeCashFlowStatementHeader> colNumHeaderMap = getColumnNumberHeaderMap(table);
				freeCashFlowStatements.addAll(extractData(table, colNumHeaderMap, context));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return freeCashFlowStatements;
	}

	@Override
	protected void populateColNumHeaderMap(Map<Integer, FreeCashFlowStatementHeader> colNumHeaderMap, Element thElement) {
		String cleanValue = thElement.text().replaceAll("\\s+", "");
		FreeCashFlowStatementHeader header = FreeCashFlowStatementHeader.getByZhTitle(cleanValue);
		if (header != null) {
			colNumHeaderMap.put(thElement.siblingIndex(), header);
		}
	}

	@Override
	protected FreeCashFlowStatement createEntity(Object... context) {
		FreeCashFlowStatement entity = new FreeCashFlowStatement();
		StockReportId id = new StockReportId();

		// 從 context 中解構出年份與季度 (假設傳入順序為 Year, Quarter)
		if (context != null && context.length >= 2) {
			id.setReportYear(((Integer) context[0]).shortValue());
			id.setReportQuarter(((Integer) context[1]).byteValue());
		}

		entity.setId(id);
		return entity;
	}

	@Override
	protected void populateEntity(FreeCashFlowStatement entity, FreeCashFlowStatementHeader header, String value) {
		switch (header) {
		case COMPANY_CODE:
			entity.getId().setStockCode(value);
			break;
		case BEGINNING_CASH_BALANCE:
			entity.setBeginningCashBalance(NumberHandleUtils.parseLong(value));
			break;
		case CASH_INCREASE_DECREASE:
			entity.setCashIncreaseDecrease(NumberHandleUtils.parseLong(value));
			break;
		case ENDING_CASH_BALANCE:
			entity.setEndingCashBalance(NumberHandleUtils.parseLong(value));
			break;
		case FINANCING_CASH_FLOW:
			entity.setFinancingCashFlow(NumberHandleUtils.parseLong(value));
			break;
		case FX_RATE_IMPACT:
			entity.setFxRateImpact(NumberHandleUtils.parseLong(value));
			break;
		case INVESTING_CASH_FLOW:
			entity.setInvestingCashFlow(NumberHandleUtils.parseLong(value));
			break;
		case OPERATING_CASH_FLOW:
			entity.setOperatingCashFlow(NumberHandleUtils.parseLong(value));
			break;
		default:
			break;
		}
	}
}
