package com.klm.order_management.order.api.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UpdatePaymentRequest(
        @NotNull(message = "Order ID is required")
        UUID orderId,
        
        @NotEmpty(message = "At least one payment is required")
        @Valid
        List<PaymentRequest> paymentRequest) {
}
