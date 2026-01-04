package com.kusoduck.securities.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CumulativeEpsDataId {

	@Column(name = "SECURITY_CODE", length = 20, nullable = false)
	private String securityCode;

	@Column(name = "YEAR", nullable = false)
	private int year;

	@Column(name = "SEASON", nullable = false)
	private int season;

	public CumulativeEpsDataId() {

	}

	public CumulativeEpsDataId(String securityCode, int year, int season) {
		this.securityCode = securityCode;
		this.year = year;
		this.season = season;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	// equals 和 hashCode 方法是必須的
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CumulativeEpsDataId epsId = (CumulativeEpsDataId) o;
		return year == epsId.year && season == epsId.season && Objects.equals(securityCode, epsId.securityCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(securityCode, year, season);
	}
}
