package com.klm.order_management.order.application;

import com.klm.order_management.order.api.requests.CreateOrderRequest;
import com.klm.order_management.order.api.requests.UpdatePaymentRequest;
import com.klm.order_management.order.api.response.CreateOrderResponse;
import com.klm.order_management.order.domain.aggregate.Order;
import com.klm.order_management.order.infrastructure.repository.OrderRepository;
import org.springframework.stereotype.Service;
import com.klm.order_management.order.application.mappers.OrderMapper;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public CreateOrderResponse add(CreateOrderRequest request) {

        var order = orderRepository.save(OrderMapper.toDomain(request));
        return new CreateOrderResponse(order.getId());
    }

    @Override
    public void updatePayment(UpdatePaymentRequest request) {

        var order = findById(request.orderId());

        order.updatePayments(request.paymentRequest());
        orderRepository.save(order);
    }

    @Override
    public Order findById(UUID id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Order with id " + id + " not found"));
    }
}
