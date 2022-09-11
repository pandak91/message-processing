package com.msg.driver;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import com.msg.entity.Message;
import com.msg.entity.MessageType1;
import com.msg.entity.MessageType2;
import com.msg.entity.MessageType3;
import com.msg.entity.Sale;
import com.msg.enums.Operation;
import com.msg.enums.ProductType;
import com.msg.service.MessageService;

public class Driver {

	static MessageService service = new MessageService();

	public static void main(String[] args) {
		Random r1 = new Random();
		for (int i = 0; i < 102; i++) {
			Message m;
			int msgType = r1.nextInt(3);
			ProductType prodType = getProdType(r1.nextInt(3));
			switch (msgType) {
			case 0:
			default:
				m = new MessageType1(new Sale(prodType, new BigDecimal(r1.nextDouble()).setScale(2, RoundingMode.CEILING)));
				break;
			case 1:
				m = new MessageType2(new Sale(prodType, new BigDecimal(r1.nextDouble()).setScale(2, RoundingMode.CEILING)), r1.nextInt(100));
				break;
			case 2:
				Operation op = getOperation(r1.nextInt(3));
				m = new MessageType3(new Sale(prodType, new BigDecimal(r1.nextDouble()).setScale(2, RoundingMode.CEILING)), op);
				break;
			}
			service.enqueue(m);
		}
		while (service.dequeueAndProcess()) {
			System.out.println("Dequeueing a message & processing");
		}
	}

	private static Operation getOperation(int operation) {
		switch (operation) {
		case 0:
		default:
			return Operation.Add;
		case 1:
			return Operation.Subtract;
		case 2:
			return Operation.Multiply;
		}
	}

	private static ProductType getProdType(int type) {
		switch (type) {
		case 0:
		default:
			return ProductType.Apple;
		case 1:
			return ProductType.Grapes;
		case 2:
			return ProductType.Melon;
		}
	}
}