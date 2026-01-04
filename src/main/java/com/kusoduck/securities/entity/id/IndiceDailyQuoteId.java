package com.kusoduck.securities.entity.id;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 透過使用 @Embeddable 和 @EmbeddedId，可以將複合主鍵的邏輯封裝在一個獨立的類別中，這使得您的實體類別更加簡潔和易於管理。
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class IndiceDailyQuoteId {

	@Column(name = "TRADE_DATE")
	private LocalDate tradeDate;

	@Column(name = "INDEX_NAME")
	private String indexName;
}
