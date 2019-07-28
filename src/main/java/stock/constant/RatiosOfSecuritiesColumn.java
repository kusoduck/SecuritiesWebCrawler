package stock.constant;

public enum RatiosOfSecuritiesColumn {
	SECURITY_CODE("證券代號"),
	SECURITY_NAME("證券名稱"),
	DIVIDEND_YIELD("殖利率(%)"),
	DIVIDEND_YEAR("股利年度"),
	PER("本益比"),
	PBR("股價淨值比"),
	FINANCIAL_YEAR_QUARTER("財報年/季");

	private String chinese;

	private RatiosOfSecuritiesColumn(String chinese) {
		this.setChinese(chinese);
	}

	public static RatiosOfSecuritiesColumn get(String value) {
		if (value != null) {
			String realValue = value.trim();
			if (!realValue.isEmpty()) {
				for (RatiosOfSecuritiesColumn key : values()) {
					if (key.getChinese().equals(realValue)) {
						return key;
					}
				}
			}
		}
		return null;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}
}
