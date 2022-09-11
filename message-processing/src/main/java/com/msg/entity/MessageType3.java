package com.msg.entity;

import com.msg.enums.Operation;

public class MessageType3 implements Message {

	Sale sale;
	Operation operation;
	
	public MessageType3(Sale sale, Operation operation) {
		super();
		this.sale = sale;
		this.operation = operation;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	@Override
	public Sale getSale() {
		// TODO Auto-generated method stub
		return sale;
	}

}
