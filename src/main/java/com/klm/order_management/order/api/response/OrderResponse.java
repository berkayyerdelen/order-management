package com.klm.order_management.order.api.response;

import com.klm.order_management.order.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID customerId,
        String bookingReference,
        OrderStatus status,
        BigDecimal totalAmount,
        String currency,
        LocalDateTime createdAt,
        int itemCount,
        int passengerCount
) {
}
