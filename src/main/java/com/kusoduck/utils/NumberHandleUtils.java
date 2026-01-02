package com.kusoduck.utils;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class NumberHandleUtils {

	// 輔助方法：處理數值轉換並移除逗號
	public static BigDecimal parseBigDecimal(String value) {
		if(StringUtils.isBlank(value)) {
			return null;
		}
		String newValue = value.trim().replace(",", "");
		if (!NumberUtils.isCreatable(newValue)) {
			return null; // 或者根據需求返回 BigDecimal.ZERO
		}
		return new BigDecimal(newValue);
	}

	public static Long parseLong(String value) {
		if(StringUtils.isBlank(value)) {
			return null;
		}
		String newValue = value.trim().replace(",", "");
		if (!NumberUtils.isCreatable(newValue)) {
			return null;
		}
		return Long.parseLong(newValue);
	}
}
