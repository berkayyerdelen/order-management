package com.klm.order_management.order.application.mappers;

import com.klm.order_management.order.api.response.OrderDetailResponse;
import com.klm.order_management.order.api.response.OrderResponse;
import com.klm.order_management.order.domain.aggregate.Order;
import com.klm.order_management.order.domain.aggregate.OrderItem;
import com.klm.order_management.order.domain.aggregate.Passenger;
import com.klm.order_management.order.domain.aggregate.Payment;

import java.util.Collections;
import java.util.stream.Collectors;

public class OrderResponseMapper {

    private OrderResponseMapper() {}

    public static OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getBookingReference().getValue(),
                order.getStatus(),
                order.getTotalPrice().getAmount(),
                order.getTotalPrice().getCurrency(),
                order.getCreatedAt(),
                order.getOrderItems() != null ? order.getOrderItems().size() : 0,
                order.getPassengers() != null ? order.getPassengers().size() : 0
        );
    }

    public static OrderDetailResponse toOrderDetailResponse(Order order) {
        return new OrderDetailResponse(
                order.getId(),
                order.getCustomerId(),
                order.getBookingReference().getValue(),
                order.getStatus(),
                order.getTotalPrice().getAmount(),
                order.getTotalPrice().getCurrency(),
                order.getCreatedAt(),
                toContactInfo(order),
                order.getOrderItems() != null ? 
                        order.getOrderItems().stream()
                                .map(OrderResponseMapper::toOrderItemInfo)
                                .collect(Collectors.toList()) : 
                        Collections.emptyList(),
                order.getPassengers() != null ?
                        order.getPassengers().stream()
                                .map(OrderResponseMapper::toPassengerInfo)
                                .collect(Collectors.toList()) :
                        Collections.emptyList()
        );
    }

    private static OrderDetailResponse.ContactInfo toContactInfo(Order order) {
        if (order.getContactInformation() == null) {
            return null;
        }
        return new OrderDetailResponse.ContactInfo(
                order.getContactInformation().getEmail(),
                order.getContactInformation().getPhoneNumber()
        );
    }

    private static OrderDetailResponse.OrderItemInfo toOrderItemInfo(OrderItem item) {
        return new OrderDetailResponse.OrderItemInfo(
                item.getId(),
                item.getDescription(),
                item.getPrice(),
                item.getQuantity(),
                item.getCurrency(),
                item.getType().name(),
                toPaymentInfo(item.getPayment())
        );
    }

    private static OrderDetailResponse.PassengerInfo toPassengerInfo(Passenger passenger) {
        return new OrderDetailResponse.PassengerInfo(
                passenger.getId(),
                passenger.getFirstName(),
                passenger.getLastName(),
                passenger.getDateOfBirth(),
                passenger.getNationality()
        );
    }

    private static OrderDetailResponse.PaymentInfo toPaymentInfo(Payment payment) {
        if (payment == null) {
            return null;
        }
        return new OrderDetailResponse.PaymentInfo(
                payment.getMethod() != null ? payment.getMethod().name() : null,
                payment.getStatus() != null ? payment.getStatus().name() : null,
                payment.getAmount() != null ? payment.getAmount().getAmount() : null,
                payment.getAmount() != null ? payment.getAmount().getCurrency() : null
        );
    }
}
