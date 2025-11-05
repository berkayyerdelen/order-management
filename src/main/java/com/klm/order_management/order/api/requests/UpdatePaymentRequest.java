package com.klm.order_management.order.api.requests;

import java.util.List;
import java.util.UUID;

public record UpdatePaymentRequest(UUID orderId, List<PaymentRequest> paymentRequest){}
