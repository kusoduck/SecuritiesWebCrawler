package com.kusoduck.stock.constant;

public enum StockDailyQuotesColumn {
	SECURITY_CODE("證券代號"),
	SECURITY_NAME("證券名稱"),
	TRADE_VOLUME("成交股數"),
	TRANSACTION("成交筆數"),
	TRADE_VALUE("成交金額"),
	OPENING_PRICE("開盤價"),
	HIGHEST_PRICE("最高價"),
	LOWEST_PRICE("最低價"),
	CLOSING_PRICE("收盤價"),
	DIRECTION("漲跌(+/-)"),
	CHANGE("漲跌價差"),
	LAST_BEST_BID_PRICE("最後揭示買價"),
	LAST_BEST_BID_VOLUME("最後揭示買量"),
	LAST_BEST_ASK_PRICE("最後揭示賣價"),
	LAST_BEST_ASK_VOLUME("最後揭示賣量"),
	PRICE_EARNING_RATIO("本益比");

	private String zhTitle;

	private StockDailyQuotesColumn(String zhTitle) {
		setZhTitle(zhTitle);
	}

	public static StockDailyQuotesColumn getByZhTitle(String value) {
		if (value != null) {
			String realValue = value.trim();
			if (!realValue.isEmpty()) {
				for (StockDailyQuotesColumn key : values()) {
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
