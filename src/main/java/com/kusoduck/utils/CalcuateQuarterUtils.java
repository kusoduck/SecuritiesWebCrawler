package com.kusoduck.utils;

import java.time.LocalDate;

import org.apache.commons.lang3.tuple.Pair;

public class CalcuateQuarterUtils {

    /**
     * 取得指定日期所屬的季度
     * @param localDate 日期
     * @return 季度 (1-4)
     */
    public static int getQuarter(LocalDate localDate) {
        int month = localDate.getMonthValue();
        return (month - 1) / 3 + 1;
    }

    /**
     * 取得指定日期的年份和季度資訊
     * @param localDate 日期
     * @return 包含年份和季度的字串，格式為 "yyyy年第Q季"
     */
    public static Pair<Integer, Integer> getYearAndQuarter(LocalDate localDate) {
        int year = localDate.getYear();
        int quarter = getQuarter(localDate);
        return Pair.of(year, quarter);
    }

    public static Pair<Integer, Integer> calcPreviousQuarter(int currentYear, int currentQuarter) {
		int previousQuarter;
		int previousYear;
		if (currentQuarter == 1) {
			previousQuarter = 4;
			previousYear = currentYear - 1;
		} else {
			previousQuarter = currentQuarter - 1;
			previousYear = currentYear;
		}

		return Pair.of(previousYear, previousQuarter);
	}
}
