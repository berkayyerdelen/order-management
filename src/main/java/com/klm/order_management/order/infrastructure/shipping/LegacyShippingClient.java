package com.klm.order_management.order.infrastructure.shipping;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class LegacyShippingClient {

    public LegacyShippingResponse createShipmentInLegacySystem(String orderRef, String destination) {
        // Simulate external system call
        System.out.println("Calling Legacy System for Order: " + orderRef);

        LegacyShippingResponse response = new LegacyShippingResponse();
        response.XML_SHIP_ID = UUID.randomUUID().toString();
        response.TRACK_REF_NO = "LEGACY-" + System.currentTimeMillis();
        response.STATUS_CODE = 99; // Simulating success
        return response;
    }
}
