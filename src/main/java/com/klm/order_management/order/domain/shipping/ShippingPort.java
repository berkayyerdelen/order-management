package com.klm.order_management.order.domain.shipping;

import java.util.UUID;

public interface ShippingPort {
    Shipment shipOrder(UUID orderId, String address);
}
