package com.klm.order_management.order.api.requests;

import java.math.BigDecimal;

public record OrderItemRequest(
        String description,
        BigDecimal price,
        int quantity,
        String currency,
        String type // e.g. "FLIGHT_TICKET", "EXTRA_BAGGAGE"
) {}
