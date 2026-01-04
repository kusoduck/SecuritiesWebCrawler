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
 * 複合主鍵類別，對應t_daily_quotes資料表的TRADE_DATE和SECURITY_CODE欄位。
 * 透過使用 @Embeddable 和 @EmbeddedId，可以將複合主鍵的邏輯封裝在一個獨立的類別中，這使得您的實體類別 DailyQuote 更加簡潔和易於管理。
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StockTradeId {

	@Column(name = "TRADE_DATE", nullable = false)
    private LocalDate tradeDate;

    @Column(name = "STOCK_CODE", nullable = false, length = 10)
    private String stockCode;
}
