package com.klm.order_management.order.api.requests;

import com.klm.order_management.order.domain.enums.PaymentMethod;

import java.util.UUID;

public record PaymentRequest(
        UUID orderItemId,
        PaymentMethod method,
        Double amount,
        String currency) {
}

