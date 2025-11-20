package com.klm.order_management.order.infrastructure.repository;

import com.klm.order_management.order.domain.aggregate.Order;
import com.klm.order_management.order.domain.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    
    /**
     * Find all orders for a specific customer
     */
    List<Order> findByCustomerId(UUID customerId);
    
    /**
     * Find all orders with a specific status
     */
    List<Order> findByStatus(OrderStatus status);
    
    /**
     * Find orders by customer ID with pagination
     */
    Page<Order> findByCustomerId(UUID customerId, Pageable pageable);
    
    /**
     * Find orders by status with pagination
     */
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    
    /**
     * Find orders by customer ID and status
     */
    List<Order> findByCustomerIdAndStatus(UUID customerId, OrderStatus status);
}
