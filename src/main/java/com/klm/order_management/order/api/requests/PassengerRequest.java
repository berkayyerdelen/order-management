package com.klm.order_management.order.api.requests;

import java.time.LocalDate;

public record PassengerRequest(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String nationality
) {}
