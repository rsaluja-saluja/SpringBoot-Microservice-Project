package com.example.orderservice.controller;

import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.orderservice.client.InventoryClient;
import com.example.orderservice.controller.dto.OrderDto;
import com.example.orderservice.controller.repo.OrderRepo;
import com.example.orderservice.model.Order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

	@Autowired
	OrderRepo orderRepo;

	@Autowired
	InventoryClient inventoryClient;

	@Autowired
	Resilience4JCircuitBreakerFactory circuitBreakerFactory;

	@Autowired
	StreamBridge streamBridge;

	@GetMapping
	public String getTestMessage() {
		return "TEST Message Hello";
	}

	@PostMapping
	public String postOrder(@RequestBody OrderDto orderDto) {
		System.out.println("OrderService postOrder Called");
		// System.out.println("Inventory message:"+inventoryClient.getTest());
//		Order order = new Order();
//		order.setOrderLineItems(orderDto.getOrderLineItemList());
//		order.setOrderNumber(UUID.randomUUID().toString());
//		orderRepo.save(order);
//		return "Order Placed Successfully";

		boolean allProductsInStock = orderDto.getOrderLineItemList().stream()
				.allMatch(orderLineItem -> inventoryClient.checkStock(orderLineItem.getSkuCode()));

		/*
		 * Resilience not working. Getting NullPointer Exception
		 */
//		Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("inventory");
//		Supplier<Boolean> booleanSupplier = () -> orderDto.getOrderLineItemList().stream()
//			.allMatch(orderLineItem -> inventoryClient.checkStock(orderLineItem.getSkuCode()));
//		boolean allProductsInStock = circuitBreaker.run(booleanSupplier, throwable -> handleErrorCase(throwable));

		if (allProductsInStock) {
			Order order = new Order();
			order.setOrderLineItems(orderDto.getOrderLineItemList());
			order.setOrderNumber(UUID.randomUUID().toString());
			orderRepo.save(order);

			log.info("Sending Order Details with Order Id {} to Notification Service", order.getId());
			streamBridge.send("notificationEventSupplier-out-0",MessageBuilder.withPayload(order.getId()).build());

			return "Order Placed Successfully";
		} else {
			return "Order Failed. One of the products not in stock";
		}
	}

//	private Boolean handleErrorCase(Throwable throwable) {
//		System.out.println("Exception: "+throwable.getLocalizedMessage());
//		System.out.println("Exception: "+throwable.getMessage());
//		System.out.println("Exception: "+throwable.toString());
//		System.out.println("Exception: "+throwable.getCause());
//		return false; 
//	}
}
