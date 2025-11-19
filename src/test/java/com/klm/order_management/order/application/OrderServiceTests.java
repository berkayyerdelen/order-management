package com.klm.order_management.order.application;

import com.klm.order_management.order.api.requests.*;
import com.klm.order_management.order.domain.aggregate.Order;
import com.klm.order_management.order.domain.enums.PaymentMethod;
import com.klm.order_management.order.infrastructure.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void should_Add_Order_Successfully() {
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest(
                UUID.randomUUID(),
                new ContactInformationRequest("john.doe@example.com", "1234567890"),
                List.of(
                        new PassengerRequest("John", "Doe", LocalDate.of(1990, 1, 1), "NL"),
                        new PassengerRequest("Jane", "Doe", LocalDate.of(1992, 5, 12), "NL")
                ),
                List.of(
                        new OrderItemRequest("Flight Ticket", BigDecimal.valueOf(500), 1, "EUR", "FLIGHT"),
                        new OrderItemRequest("Extra Baggage", BigDecimal.valueOf(50), 2, "EUR", "FLIGHT")
                ),
                new MoneyRequest(BigDecimal.valueOf(600), "EUR")
        );
        var id = UUID.randomUUID();
        ;
        Order savedOrder = Mockito.mock(Order.class);
        Mockito.when(savedOrder.getId()).thenReturn(id);
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(savedOrder);
        // Act
        var response = orderService.add(request);
        // Assert
        Assertions.assertEquals(savedOrder.getId(), response.orderId());
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Order.class));

    }

    @Test
    void should_Throw_Exception_When_Order_Not_Found() {
        // Arrange
        UpdatePaymentRequest request = new UpdatePaymentRequest(
                UUID.randomUUID(), 
                List.of(new PaymentRequest(UUID.randomUUID(), PaymentMethod.CARD, BigDecimal.valueOf(600), "EUR"))
        );
        Mockito.when(orderRepository.findById(request.orderId()))
                .thenReturn(java.util.Optional.empty());
        // Act & Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            orderService.updatePayment(request);
        });
        Mockito.verify(orderRepository, Mockito.times(1)).findById(request.orderId());
    }
}
