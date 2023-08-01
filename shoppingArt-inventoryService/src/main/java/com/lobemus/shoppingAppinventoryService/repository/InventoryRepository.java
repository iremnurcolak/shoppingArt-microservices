package com.lobemus.shoppingAppinventoryService.repository;

import com.lobemus.shoppingAppinventoryService.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findBySkuCode(String skuCode);
}
