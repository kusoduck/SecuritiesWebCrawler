package com.kusoduck.utils;

import java.time.LocalDate;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalcuateQuarterUtils {
	private static Logger logger = LoggerFactory.getLogger(CalcuateQuarterUtils.class);

	/**
	 * 取得指定日期所屬的季度
	 *
	 * @param localDate 日期
	 * @return 季度 (1-4)
	 */
	public static int getQuarter(LocalDate localDate) {
		int month = localDate.getMonthValue();
		return (month - 1) / 3 + 1;
	}

	/**
	 * 取得指定日期的年份和季度資訊
	 *
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

	/**
	 * 【新】直接根據日期計算前一季度的年和季度。 * @param date 待計算的日期。
	 *
	 * @return 包含 (前一季度年, 前一季度季度) 的 Pair。
	 */
	public static Pair<Integer, Integer> calcPreviousQuarter(LocalDate date) {
		Pair<Integer, Integer> currentYearQuarter = getYearAndQuarter(date);
		return calcPreviousQuarter(currentYearQuarter.getLeft(), currentYearQuarter.getRight());
	}

	public static Pair<Integer, Integer> calcPreviousYearQuarter(LocalDate current) {

		Pair<Integer, Integer> previousYearQuarter = calcPreviousQuarter(current);

		logger.debug(previousYearQuarter.getLeft() + "-" + previousYearQuarter.getRight()); // 假設您有日誌記錄

		int year = previousYearQuarter.getLeft();
		int quarter = previousYearQuarter.getRight();

		return Pair.of(year, quarter);
	}

	/**
	 * 將西元年轉換為中華民國紀元 (ROC Year)。 * @param year 西元年 (例如 2025)。
	 *
	 * @return 中華民國紀元年份 (例如 114)。
	 */
	public static int toRocYear(int year) {
		return year - 1911;
	}
}
