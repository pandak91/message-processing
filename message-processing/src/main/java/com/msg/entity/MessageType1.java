package com.msg.entity;

public class MessageType1 implements Message {

	Sale sale;

	public MessageType1(Sale sale) {
		super();
		this.sale = sale;
	}

	@Override
	public Sale getSale() {
		// TODO Auto-generated method stub
		return sale;
	}
}
