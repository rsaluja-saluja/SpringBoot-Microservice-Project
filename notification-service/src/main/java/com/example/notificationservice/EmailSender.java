package com.example.notificationservice;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailSender {

	public void sendEmail(String orderNumber) {
		System.out.println("Order Placed successfully - Ordre Number is : "+orderNumber);
		System.out.println("Email Sent");
		log.info("Order Placed successfully - Ordre Number is : "+orderNumber);
		log.info("Email Sent");
	}
}
