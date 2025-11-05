package com.klm.order_management.order.application.mappers;

import com.klm.order_management.order.api.requests.ContactInformationRequest;
import com.klm.order_management.order.api.requests.CreateOrderRequest;
import com.klm.order_management.order.api.requests.OrderItemRequest;
import com.klm.order_management.order.api.requests.PassengerRequest;
import com.klm.order_management.order.domain.aggregate.Order;
import com.klm.order_management.order.domain.aggregate.OrderItem;
import com.klm.order_management.order.domain.aggregate.Passenger;
import com.klm.order_management.order.domain.enums.OrderItemType;
import com.klm.order_management.order.domain.valueobject.BookingReference;
import com.klm.order_management.order.domain.valueobject.ContactInformation;
import com.klm.order_management.order.domain.valueobject.Money;

import java.util.UUID;

public class OrderMapper {

    private OrderMapper() {}

    // --- API -> Domain ---

    public static Order toDomain(CreateOrderRequest request) {
        Money totalPrice = new Money(request.totalPrice().amount().doubleValue(), request.totalPrice().currency());
        BookingReference reference = new BookingReference(UUID.randomUUID().toString());

        Order order = new Order(request.customerId(), reference, totalPrice);
        order.setContactInformation(toDomain(request.contactInformation()));

        if (request.orderItems() != null) {
            request.orderItems().forEach(item -> order.getOrderItems().add(toDomain(item, order)));
        }

        if (request.passengers() != null) {
            request.passengers().forEach(p -> order.addPassenger(toDomain(p)));
        }

        return order;
    }

    private static ContactInformation toDomain(ContactInformationRequest dto) {
        return new ContactInformation(dto.email(), dto.phoneNumber());
    }

    private static OrderItem toDomain(OrderItemRequest dto, Order order) {
        return new OrderItem(order,
                OrderItemType.valueOf(dto.type()),
                dto.description(),
                dto.price(),
                dto.quantity(),
                dto.currency());
    }

    private static Passenger toDomain(PassengerRequest dto) {
        return new Passenger(dto.firstName(), dto.lastName(), dto.dateOfBirth());
    }
}
