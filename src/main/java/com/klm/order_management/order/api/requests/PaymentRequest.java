package com.klm.order_management.order.api.requests;

import com.klm.order_management.order.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
        @NotNull(message = "Order item ID is required")
        UUID orderItemId,
        
        @NotNull(message = "Payment method is required")
        PaymentMethod method,
        
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,
        
        @NotBlank(message = "Currency is required")
        String currency) {
}

