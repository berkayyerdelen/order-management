package com.klm.order_management.order.api.requests;

import java.math.BigDecimal;

public record MoneyRequest(
        BigDecimal amount,
        String currency
) {}
