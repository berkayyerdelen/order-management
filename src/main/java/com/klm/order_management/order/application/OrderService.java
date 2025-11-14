package com.klm.order_management.order.application;

import com.klm.order_management.order.api.requests.CreateOrderRequest;
import com.klm.order_management.order.api.requests.UpdatePaymentRequest;
import com.klm.order_management.order.api.response.CreateOrderResponse;
import com.klm.order_management.order.domain.aggregate.Order;

import java.util.UUID;


public interface OrderService {
    CreateOrderResponse add(CreateOrderRequest order);
    void updatePayment(UpdatePaymentRequest request);
    Order findById(UUID id);
}

