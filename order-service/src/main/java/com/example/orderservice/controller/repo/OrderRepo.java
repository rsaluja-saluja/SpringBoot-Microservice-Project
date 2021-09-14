package com.example.orderservice.controller.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orderservice.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

}
