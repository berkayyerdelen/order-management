package com.klm.order_management.order.domain.aggregate;

import com.klm.order_management.order.api.requests.PaymentRequest;
import com.klm.order_management.order.domain.enums.OrderStatus;
import com.klm.order_management.order.domain.valueobject.BookingReference;
import com.klm.order_management.order.domain.valueobject.ContactInformation;
import com.klm.order_management.order.domain.valueobject.Money;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID customerId;

    @Embedded
    private BookingReference bookingReference;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;

    @Embedded
    private Money totalPrice;

    @Embedded
    private ContactInformation contactInformation;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Passenger> passengers = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    protected Order() {
    }

    public Order(UUID customerId, BookingReference bookingReference, Money totalPrice) {
        this.customerId = customerId;
        this.bookingReference = bookingReference;
        this.totalPrice = totalPrice;
        this.status = OrderStatus.PENDING_PAYMENT;
        this.createdAt = LocalDateTime.now();
    }

    // domain methods
    public void addPassenger(Passenger passenger) {
        passenger.setOrder(this);
        passengers.add(passenger);
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setOrder(this);
    }

    public void updatePayments(List<PaymentRequest> paymentRequests) {

        var orderItemMap = orderItems.stream()
                .collect(Collectors.toMap(OrderItem::getId, Function.identity()));

        // First: validate all requests exist
        List<UUID> missingIds = paymentRequests.stream()
                .map(PaymentRequest::orderItemId)
                .filter(id -> !orderItemMap.containsKey(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new IllegalArgumentException(
                    "Order items not found for ids: " + missingIds);
        }

        for (var request : paymentRequests) {
            var item = orderItemMap.get(request.orderItemId());
            item.setPayment(new Payment(
                    request.method(),
                    new Money(request.amount(), request.currency())
            ));
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setBookingReference(BookingReference bookingReference) {
        this.bookingReference = bookingReference;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Money totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public BookingReference getBookingReference() {
        return bookingReference;
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
