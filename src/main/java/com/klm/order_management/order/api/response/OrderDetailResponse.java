package com.klm.order_management.order.api.response;

import com.klm.order_management.order.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDetailResponse(
        UUID id,
        UUID customerId,
        String bookingReference,
        OrderStatus status,
        BigDecimal totalAmount,
        String currency,
        LocalDateTime createdAt,
        ContactInfo contactInformation,
        List<OrderItemInfo> orderItems,
        List<PassengerInfo> passengers
) {
    public record ContactInfo(String email, String phoneNumber) {}
    
    public record OrderItemInfo(
            UUID id,
            String description,
            BigDecimal price,
            int quantity,
            String currency,
            String type,
            PaymentInfo payment
    ) {}
    
    public record PaymentInfo(
            String method,
            String status,
            BigDecimal amount,
            String currency
    ) {}
    
    public record PassengerInfo(
            UUID id,
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String nationality
    ) {}
}
