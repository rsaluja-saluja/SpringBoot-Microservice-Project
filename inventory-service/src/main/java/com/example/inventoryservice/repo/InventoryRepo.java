package com.example.inventoryservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inventoryservice.model.Inventory;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long>{

	Optional<Inventory> findBySkuCode(String skuCode);


}
