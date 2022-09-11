package com.msg.entity;

import java.math.BigDecimal;

public class ProductDetails {
	int quantity;
	BigDecimal totalPrice;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ProductDetails(int quantity, BigDecimal totalPrice) {
		super();
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public void addPrice(BigDecimal price) {
		this.totalPrice = this.totalPrice.add(price);
	}
	
	public void subtractPrice(BigDecimal price) {
		this.totalPrice = this.totalPrice.subtract(price);
	}
	
	public void multiplyPrice(BigDecimal price) {
		this.totalPrice = this.totalPrice.multiply(price);
	}
	
	@Override
	public String toString() {
		return "ProductDetails [quantity=" + quantity + ", totalPrice=" + totalPrice + "]";
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}
}
