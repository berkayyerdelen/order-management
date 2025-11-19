package com.klm.order_management.order.api.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        @NotNull(message = "Customer ID is required")
        UUID customerId,
        
        @NotNull(message = "Contact information is required")
        @Valid
        ContactInformationRequest contactInformation,
        
        @NotEmpty(message = "At least one passenger is required")
        @Valid
        List<PassengerRequest> passengers,
        
        @NotEmpty(message = "At least one order item is required")
        @Valid
        List<OrderItemRequest> orderItems,
        
        @NotNull(message = "Total price is required")
        @Valid
        MoneyRequest totalPrice
) {
}


