package com.msg.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import org.junit.Test;

import com.msg.entity.Message;
import com.msg.entity.MessageType1;
import com.msg.entity.MessageType2;
import com.msg.entity.MessageType3;
import com.msg.entity.Sale;
import com.msg.enums.Operation;
import com.msg.enums.ProductType;
import com.msg.service.MessageService;

import junit.framework.Assert;

public class MessageServiceTest {

	MessageService service = new MessageService();

	@Test
	public void testQueueEmpty() {
		Assert.assertEquals(false, service.dequeueAndProcess());
	}
	
	@Test
	public void testWithSingleMessageType1() {
		MessageType1 m = new MessageType1(new Sale(ProductType.Apple, new BigDecimal(10)));
		service.enqueue(m);
		Assert.assertEquals(true, service.dequeueAndProcess());
	}
	
	@Test
	public void testNullCase() {
		service.enqueue(null);
		Assert.assertEquals(true, service.dequeueAndProcess());
	}
	
	@Test
	public void testMessageType3() {
		MessageType1 m1 = new MessageType1(new Sale(ProductType.Apple, new BigDecimal(10)));
		service.enqueue(m1);
		service.dequeueAndProcess();
		MessageType3 m3 = new MessageType3(new Sale(ProductType.Apple, new BigDecimal(5)), Operation.Add);
		service.enqueue(m3);
		service.dequeueAndProcess();
		Assert.assertEquals(0, service.getQueueSize());
	}
	
	@Test
	public void testEntireHappyPathFlow() {
		Random r1 = new Random();
		for (int i = 0; i < 102; i++) {
			Message m;
			int msgType = r1.nextInt(3);
			ProductType prodType = getProdType(r1.nextInt(3));
			switch (msgType) {
			case 0:
			default:
				m = new MessageType1(
						new Sale(prodType, new BigDecimal(r1.nextDouble()).setScale(2, RoundingMode.CEILING)));
			case 1:
				m = new MessageType2(
						new Sale(prodType, new BigDecimal(r1.nextDouble()).setScale(2, RoundingMode.CEILING)),
						r1.nextInt(100));
			case 2:
				Operation op = getOperation(r1.nextInt(3));
				m = new MessageType3(
						new Sale(prodType, new BigDecimal(r1.nextDouble()).setScale(2, RoundingMode.CEILING)), op);
			}
			service.enqueue(m);
		}
		while (service.dequeueAndProcess()) {
			System.out.println("Dequeueing a message & processing");
		}
		Assert.assertEquals(0, service.getQueueSize());
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