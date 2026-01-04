package com.kusoduck.securities.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * 透過使用 @Embeddable 和 @EmbeddedId，可以將複合主鍵的邏輯封裝在一個獨立的類別中，這使得您的實體類別更加簡潔和易於管理。
 */
@Embeddable
public class IndiceDailyQuoteId {

	@Column(name = "DATE")
	private LocalDate date;

	@Column(name = "INDEX_NAME")
	private String indexName;

	public IndiceDailyQuoteId() {
	}

	public IndiceDailyQuoteId(LocalDate date, String indexName) {
		this.date = date;
		this.indexName = indexName;
	}

	// Getters and Setters
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		IndiceDailyQuoteId that = (IndiceDailyQuoteId) o;
		return Objects.equals(date, that.date) && Objects.equals(indexName, that.indexName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, indexName);
	}
}
