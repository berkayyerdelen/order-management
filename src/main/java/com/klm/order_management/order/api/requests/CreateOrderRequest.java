package com.klm.order_management.order.api.requests;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        UUID customerId,
        ContactInformationRequest contactInformation,
        List<PassengerRequest> passengers,
        List<OrderItemRequest> orderItems,
        MoneyRequest totalPrice
) {
}


