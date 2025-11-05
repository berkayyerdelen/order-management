package com.klm.order_management.order.domain.enums;

public enum PaymentStatus {
    AUTHORIZED, CAPTURED, FAILED, PENDING;

    public boolean isAuthorizedOrCaptured() {
        return this == AUTHORIZED || this == CAPTURED;
    }
}
