package com.msg.entity;

public class MessageType2 implements Message {

	Sale sale;
	int occurance;

	public MessageType2(Sale sale, int occurance) {
		super();
		this.sale = sale;
		this.occurance = occurance;
	}

	@Override
	public Sale getSale() {
		// TODO Auto-generated method stub
		return sale;
	}

	public int getOccurance() {
		return occurance;
	}

	public void setOccurance(int occurance) {
		this.occurance = occurance;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

}
