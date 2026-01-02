package com.kusoduck.stock.constant;

public enum IndexDailyQuotesHeader {
	INDEX_NAME("指數"),
	CLOSING_INDEX("收盤指數"),
	DIRECTION("漲跌(+/-)"),
	CHANGE("漲跌點數"),
	CHANGE_PERCENT("漲跌百分比(%)"),
	NOTE("特殊處理註記");

	private String zhTitle;

	private IndexDailyQuotesHeader(String zhTitle) {
		setZhTitle(zhTitle);
	}

	public static IndexDailyQuotesHeader getByZhTitle(String value) {
		if (value != null) {
			String realValue = value.trim();
			if (!realValue.isEmpty()) {
				for (IndexDailyQuotesHeader key : values()) {
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
