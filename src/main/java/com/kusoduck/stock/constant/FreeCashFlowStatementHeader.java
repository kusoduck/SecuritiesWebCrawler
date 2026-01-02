package com.kusoduck.stock.constant;

import com.kusoduck.utils.HeaderUtils;

public enum FreeCashFlowStatementHeader implements ChineseHeader {
	COMPANY_CODE("公司代號"),
	COMPANY_NAME("公司名稱"),
	OPERATING_CASH_FLOW("營業活動之淨現金流入（流出）"),
	INVESTING_CASH_FLOW("投資活動之淨現金流入（流出）"),
	FINANCING_CASH_FLOW("籌資活動之淨現金流入（流出）"),
	FX_RATE_IMPACT("匯率變動對現金及約當現金之影響"),
	CASH_INCREASE_DECREASE("本期現金及約當現金增加（減少）數"),
	BEGINNING_CASH_BALANCE("期初現金及約當現金餘額"),
	ENDING_CASH_BALANCE("期末現金及約當現金餘額");

	private String zhTitle;

	FreeCashFlowStatementHeader(String zhTitle) {
		this.zhTitle = zhTitle;
	}

	@Override
	public String getZhTitle() {
		return zhTitle;
	}

	public static FreeCashFlowStatementHeader getByZhTitle(String value) {
		return HeaderUtils.getByZhTitle(FreeCashFlowStatementHeader.class, value);
	}


}
