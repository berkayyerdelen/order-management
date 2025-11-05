package com.klm.order_management.order.application;

import com.klm.order_management.order.api.requests.CreateOrderRequest;
import com.klm.order_management.order.api.requests.UpdatePaymentRequest;
import com.klm.order_management.order.api.response.CreateOrderResponse;
import com.klm.order_management.order.infrastructure.repository.OrderRepository;
import org.springframework.stereotype.Service;
import com.klm.order_management.order.application.mappers.OrderMapper;

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

        var order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + request.orderId()));

        order.updatePayments(request.paymentRequest());

        orderRepository.save(order);
    }
}
