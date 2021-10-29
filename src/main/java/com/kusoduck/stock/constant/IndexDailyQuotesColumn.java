package com.kusoduck.stock.constant;

public enum IndexDailyQuotesColumn {
	INDEX_NAME("指數"),
	CLOSING_INDEX("收盤指數"),
	DIRECTION("漲跌(+/-)"),
	CHANGE("漲跌點數"),
	CHANGE_PERCENT("漲跌百分比(%)"),
	NOTE("特殊處理註記");

	private String zhTitle;

	private IndexDailyQuotesColumn(String zhTitle) {
		setZhTitle(zhTitle);
	}

	public static IndexDailyQuotesColumn getByZhTitle(String value) {
		if (value != null) {
			String realValue = value.trim();
			if (!realValue.isEmpty()) {
				for (IndexDailyQuotesColumn key : values()) {
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
