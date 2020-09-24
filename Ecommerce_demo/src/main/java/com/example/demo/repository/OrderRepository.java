package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByIdAndIsActiveTrue(Long orderId);
    List<Order> findByTransactionIdAndIsActiveTrue(String transactionId);
}
