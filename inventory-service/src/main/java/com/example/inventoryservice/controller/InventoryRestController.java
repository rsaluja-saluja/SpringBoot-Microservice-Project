package com.example.inventoryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repo.InventoryRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
public class InventoryRestController {

	@Autowired
	private InventoryRepo inventoryRepo;
	
	@GetMapping("/test")
	public String getTest() {
		return "Hello Test";
	}
	@GetMapping("/{skuCode}")
	public Boolean isInStock(@PathVariable String skuCode) {
		System.out.println("Inventory Called");
		log.info("Inventory check request for skucode :"+skuCode);
		Inventory inventory = inventoryRepo.findBySkuCode(skuCode)
						.orElseThrow(() -> new RuntimeException("Can not find product by skuCode: "+skuCode));
		return inventory.getStock() > 0 ;
	}
	
}
