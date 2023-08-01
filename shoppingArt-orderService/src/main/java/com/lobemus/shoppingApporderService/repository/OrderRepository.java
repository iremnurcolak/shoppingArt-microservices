package com.lobemus.shoppingApporderService.repository;

import com.lobemus.shoppingApporderService.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
