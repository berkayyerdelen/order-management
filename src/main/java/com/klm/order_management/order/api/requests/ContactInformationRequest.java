package com.klm.order_management.order.api.requests;

public record ContactInformationRequest(
        String email,
        String phoneNumber
) {}
