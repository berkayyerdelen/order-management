package com.klm.order_management.order.application;

import com.klm.order_management.order.api.requests.CreateOrderRequest;
import com.klm.order_management.order.api.requests.UpdatePaymentRequest;
import com.klm.order_management.order.api.response.CreateOrderResponse;
import com.klm.order_management.order.domain.aggregate.Order;
import com.klm.order_management.order.infrastructure.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.klm.order_management.order.application.mappers.OrderMapper;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public CreateOrderResponse add(CreateOrderRequest request) {
        log.info("Creating order for customer: {}", request.customerId());
        try {
            var order = orderRepository.save(OrderMapper.toDomain(request));
            log.info("Order created successfully with ID: {}", order.getId());
            return new CreateOrderResponse(order.getId());
        } catch (Exception e) {
            log.error("Failed to create order for customer: {}", request.customerId(), e);
            throw e;
        }
    }

    @Transactional
    @Override
    public void updatePayment(UpdatePaymentRequest request) {
        log.info("Updating payment for order: {}", request.orderId());
        var order = findById(request.orderId());
        order.updatePayments(request.paymentRequest());
        orderRepository.save(order);
        log.info("Payment updated successfully for order: {}", request.orderId());
    }

    @Override
    public Order findById(UUID id) {
        log.debug("Finding order by ID: {}", id);
        return orderRepository.findById(id).orElseThrow(() -> {
            log.warn("Order not found with ID: {}", id);
            return new NoSuchElementException("Order with id " + id + " not found");
        });
    }
}
