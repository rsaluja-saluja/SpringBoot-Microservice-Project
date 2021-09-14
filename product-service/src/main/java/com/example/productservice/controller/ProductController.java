package com.example.productservice.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepo;

@RestController
@RequestMapping("/api/product")
@RefreshScope
public class ProductController {

	@Autowired
	private ProductRepo prodRepo;
	
	@Value("${test.name}")
	String name;
	
	@GetMapping("/test")
	public String getTest() {
		return name;
	}
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Product> findAll() {
		return prodRepo.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createProduct(@RequestBody Product product) {
		prodRepo.save(product);
	}
}
