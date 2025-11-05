package com.klm.order_management.order.domain.aggregate;

import com.klm.order_management.order.domain.enums.PaymentMethod;
import com.klm.order_management.order.domain.enums.PaymentStatus;
import com.klm.order_management.order.domain.valueobject.Money;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Embedded
    private Money amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", unique = true) // ensures one-to-one
    private OrderItem orderItem;

    protected Payment() { }

    public Payment(PaymentMethod method, Money amount) {
        this.method = method;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    public void markAuthorized() {
        this.status = PaymentStatus.AUTHORIZED;
    }

    public void markCaptured() {
        this.status = PaymentStatus.CAPTURED;
    }

    public void markFailed() {
        this.status = PaymentStatus.FAILED;
    }

    void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Money getAmount() {
        return amount;
    }
}


