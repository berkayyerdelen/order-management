package com.klm.order_management.order.domain.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public class Money {
    private double amount;
    private String currency;

    protected Money() { }

    public Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
}
