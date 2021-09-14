package com.example.orderservice.controller.dto;

import java.util.List;

import com.example.orderservice.model.OrderLineItems;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
	private List<OrderLineItems> orderLineItemList;
}
