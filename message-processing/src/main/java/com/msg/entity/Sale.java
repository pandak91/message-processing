package com.msg.entity;

import java.math.BigDecimal;

import com.msg.enums.ProductType;

public class Sale {

	public Sale(ProductType prodType, BigDecimal value) {
		super();
		this.prodType = prodType;
		this.value = value;
	}

	ProductType prodType;
	BigDecimal value;

	public ProductType getProdType() {
		return prodType;
	}

	public void setProdType(ProductType prodType) {
		this.prodType = prodType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}