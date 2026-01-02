package com.kusoduck.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {

	public static LocalDate convert(String dateString) {
        // 建立一個 DateTimeFormatter 來匹配 YYYYMMDD 格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 使用 LocalDate.parse() 方法進行轉換
        return LocalDate.parse(dateString, formatter);
	}
}
