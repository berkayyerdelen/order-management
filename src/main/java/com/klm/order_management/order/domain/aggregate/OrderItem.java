package com.klm.order_management.order.domain.aggregate;

import com.klm.order_management.order.domain.enums.OrderItemType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue()
    private UUID id;

    // Reference to parent Order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Example: FLIGHT_TICKET, EXTRA_BAGGAGE, SEAT_UPGRADE
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private OrderItemType type;

    @Column(nullable = false)
    private String description; // e.g. "AMS → JFK Flight Ticket"

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // precise monetary amount

    @Column(nullable = false)
    private int quantity;

    // Currency ISO code (e.g., EUR, USD)
    @Column(nullable = false, length = 3)
    private String currency;

    public OrderItem() {
    }

    public OrderItem(Order order, OrderItemType type, String description,
                     BigDecimal price, int quantity, String currency) {
        this.order = order;
        this.type = type;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.currency = currency;
    }

    @OneToOne(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Payment payment;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        if (payment != null) {
            payment.setOrderItem(this); // set the reverse link
        }
    }

    // --- Getters & Setters ---
    public UUID getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItemType getType() {
        return type;
    }

    public void setType(OrderItemType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}

