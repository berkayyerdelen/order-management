package com.klm.order_management.order.domain.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public class BookingReference {
    private String value;

    protected BookingReference() { }

    public BookingReference(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Booking reference cannot be empty");
        }
        this.value = value;
    }

    public String getValue() { return value; }
}
