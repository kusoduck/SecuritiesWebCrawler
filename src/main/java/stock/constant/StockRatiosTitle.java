package stock.constant;

public enum StockRatiosTitle {
	CODE("證券代號"),
	NAME("證券名稱"),
	DIVIDEND_YIELD("殖利率(%)"),
	DIVIDEND_YEAR("股利年度"),
	PER("本益比"),
	PBR("股價淨值比"),
	FINANCIAL_YEAR_QUARTER("財報年/季");

	private String zhTitle;

	private StockRatiosTitle(String zhTitle) {
		this.setZhTitle(zhTitle);
	}

	public static StockRatiosTitle get(String value) {
		if (value != null) {
			String realValue = value.trim();
			if (!realValue.isEmpty()) {
				for (StockRatiosTitle key : values()) {
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
