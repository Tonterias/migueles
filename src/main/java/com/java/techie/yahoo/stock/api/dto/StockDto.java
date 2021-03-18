package com.java.techie.yahoo.stock.api.dto;

import java.math.BigDecimal;

// Getter setter toString all args noargs
public class StockDto {

	private String name;
	private BigDecimal price;
	private BigDecimal change;
	private String currency;
	private BigDecimal bid;

	public StockDto() {
	}

	public StockDto(String name, BigDecimal price, BigDecimal change, String currency, BigDecimal bid) {
		this.name = name;
		this.price = price;
		this.change = change;
		this.currency = currency;
		this.bid = bid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getChange() {
		return change;
	}

	public void setChange(BigDecimal change) {
		this.change = change;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}

	@Override
	public String toString() {
		return "StockDto [name=" + name + ", price=" + price + ", change=" + change + ", currency=" + currency
				+ ", bid=" + bid + "]";
	}

}
