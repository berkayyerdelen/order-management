package com.klm.order_management.order.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemRequest(
        @NotBlank(message = "Description is required")
        String description,
        
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,
        
        @Positive(message = "Quantity must be positive")
        int quantity,
        
        @NotBlank(message = "Currency is required")
        String currency,
        
        @NotBlank(message = "Type is required")
        String type // e.g. "FLIGHT_TICKET", "EXTRA_BAGGAGE"
) {}
