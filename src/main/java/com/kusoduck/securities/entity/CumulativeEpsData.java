package com.kusoduck.securities.entity;

import java.math.BigDecimal;

import com.kusoduck.securities.entity.id.StockReportId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_stock_eps")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CumulativeEpsData {

	@EmbeddedId
	private StockReportId id;

	@Column(name = "eps")
	private BigDecimal eps;

}
