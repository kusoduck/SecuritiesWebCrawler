package com.kusoduck.securities.entity.id;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DividendDataId {

    /**
     * 除權息日期.
     */
    @Column(name = "ex_dividend_date", nullable = false)
    private LocalDate exDividendDate;

    /**
     * 股票代號.
     */
    @Column(name = "stock_code", nullable = false, length = 10)
    private String stockCode;
}
