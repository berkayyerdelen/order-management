package com.klm.order_management.order.domain.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public class ContactInformation {
    private String email;
    private String phoneNumber;

    protected ContactInformation() { }

    public ContactInformation(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
}
