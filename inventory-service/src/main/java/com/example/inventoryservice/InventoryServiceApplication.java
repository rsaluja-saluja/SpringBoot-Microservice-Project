package com.example.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repo.InventoryRepo;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	
	 @Bean
	    public CommandLineRunner demo(InventoryRepo repo) {
	        return (args) -> {
	           
	            repo.save(new Inventory(1L, "IPHONE12_RED", 100));
	            repo.save(new Inventory(2L, "IPHONE12_GREY", 0));


	        };
	    }
}
