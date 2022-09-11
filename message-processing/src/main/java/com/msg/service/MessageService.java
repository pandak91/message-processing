package com.msg.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.msg.entity.Message;
import com.msg.entity.MessageType1;
import com.msg.entity.MessageType2;
import com.msg.entity.MessageType3;
import com.msg.entity.ProductDetails;
import com.msg.enums.Operation;
import com.msg.enums.ProductType;

public class MessageService {

	private LinkedList<Message> q = new LinkedList<>();
	private int count = 0;
	private Map<ProductType, ProductDetails> dataMap = new HashMap<>();
	private Map<ProductType, List<MessageType3>> adjustMap = new HashMap<>();
	
	public int getQueueSize() {
		return q.size();
	}

	public void process(Message m) {
		if(m == null) {
			return;
		}
		if(!dataMap.containsKey(m.getSale().getProdType())) {
			//Adding fresh entry in HashMap for all message types.
			dataMap.put(m.getSale().getProdType(), new ProductDetails(0, BigDecimal.ZERO));
		}
		ProductDetails pd = dataMap.get(m.getSale().getProdType());
		if (m instanceof MessageType1) {
			pd.addPrice(m.getSale().getValue());
			pd.addQuantity(1);
		} else if (m instanceof MessageType2) {
			pd.addPrice(m.getSale().getValue());
			pd.addQuantity(((MessageType2) m).getOccurance());
		} else {
			processMessageType3(m, pd);
		}
		count++;
		if (count % 50 == 0) {
			try {
				System.out.println("Pausing the app now, not accepting new messages");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			printAdjustments();
		} else if (count % 10 == 0) {
			System.out.println("Print Log Report");
			for (ProductType prodType : dataMap.keySet()) {
				System.out.println("Prod Type : " + prodType + " Total Quantity : " + dataMap.get(prodType).getQuantity()
						+ " Total Price : " + dataMap.get(prodType).getTotalPrice());
			}
		}
	}

	private void printAdjustments() {
		System.out.println("Print Adjustment Summary");
		for (ProductType prodType : adjustMap.keySet()) {
			for (MessageType3 m : adjustMap.get(prodType)) {
				System.out.println("Prod Type : " + prodType + " Adjustment Value : " + m.getSale().getValue()
						+ " Adjustment Operation : " + m.getOperation());
			}
		}
	}

	private void processMessageType3(Message m, ProductDetails pd) {
		if (!adjustMap.containsKey(m.getSale().getProdType())) {
			adjustMap.put(m.getSale().getProdType(), new ArrayList<>());
		}
		adjustMap.get(m.getSale().getProdType()).add((MessageType3) m);
		int pdQuantity = pd.getQuantity();
		BigDecimal priceToBeAdjusted = m.getSale().getValue();
		Operation operation = ((MessageType3) m).getOperation();
		switch (operation) {
		case Add:
			pd.addPrice(priceToBeAdjusted.multiply(new BigDecimal(pdQuantity).setScale(2, RoundingMode.CEILING)));
			break;
		case Subtract:
			pd.subtractPrice(priceToBeAdjusted.multiply(new BigDecimal(pdQuantity).setScale(2, RoundingMode.CEILING)));
			break;
		case Multiply:
			pd.setTotalPrice(pd.getTotalPrice().multiply(priceToBeAdjusted).setScale(2, RoundingMode.CEILING));
			break;
		}
		System.out.println(dataMap);
	}

	public void enqueue(Message m) {
		q.offer(m);
	}

	public boolean dequeueAndProcess() {
		if (!q.isEmpty()) {
			process(q.poll());
			return true;
		} else {
			return false;
		}
	}
}