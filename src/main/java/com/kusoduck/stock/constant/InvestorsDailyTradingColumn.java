package com.kusoduck.stock.constant;

public enum InvestorsDailyTradingColumn {
	SECURITY_CODE("證券代號"),
	SECURITY_NAME("證券名稱"),
	FOREIGN_INVESTORS_TOTAL_BUY("外陸資買進股數(不含外資自營商)"),
	FOREIGN_INVESTORS_TOTAL_SELL("外陸資賣出股數(不含外資自營商)"),
	FOREIGN_INVESTORS_DIFFERENCE("外陸資買賣超股數(不含外資自營商)"),
	FOREIGN_DEALERS_TOTAL_BUY("外資自營商買進股數"),
	FOREIGN_DEALERS_TOTAL_SELL("外資自營商賣出股數"),
	FOREIGN_DEALERS_DIFFERENCE("外資自營商買賣超股數"),
	SECURITIES_INVESTMENT_TRUST_COMPANIES_TOTAL_BUY("投信買進股數"),
	SECURITIES_INVESTMENT_TRUST_COMPANIES_TOTAL_SELL("投信賣出股數"),
	SECURITIES_INVESTMENT_TRUST_COMPANIES_TOTAL_DIFFERENCE("投信買賣超股數"),
	DEALERS_DIFFERENCE("自營商買賣超股數"),
	DEALERS_PROPRIETARY_TOTAL_BUY("自營商買進股數(自行買賣)"),
	DEALERS_PROPRIETARY_TOTAL_SELL("自營商賣出股數(自行買賣)"),
	DEALERS_PROPRIETARY_DIFFERENCE("自營商買賣超股數(自行買賣)"),
	DEALERS_HEDGE_TOTAL_BUY("自營商買進股數(避險)"),
	DEALERS_HEDGE_TOTAL_SELL("自營商賣出股數(避險)"),
	DEALERS_HEDGE_DIFFERENCE("自營商買賣超股數(避險)"),
	TOTAL_DIFFERENCE("三大法人買賣超股數");

	private String zhTitle;

	InvestorsDailyTradingColumn(String zhTitle) {
		setZhTitle(zhTitle);
	}

	public static InvestorsDailyTradingColumn getByZhTitle(String value) {
		if (value != null) {
			String realValue = value.trim();
			if (!realValue.isEmpty()) {
				for (InvestorsDailyTradingColumn key : values()) {
					if (key.getZhTitle().equals(realValue)) {
						return key;
					}
				}
			}
		}
		return null;
	}

	public String getZhTitle() {
		return zhTitle;
	}

	private void setZhTitle(String zhTitle) {
		this.zhTitle = zhTitle;
	}

}
