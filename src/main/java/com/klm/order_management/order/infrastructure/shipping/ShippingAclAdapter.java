package com.klm.order_management.order.infrastructure.shipping;

import com.klm.order_management.order.domain.shipping.Shipment;
import com.klm.order_management.order.domain.shipping.ShippingPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShippingAclAdapter implements ShippingPort {

    private final LegacyShippingClient legacyClient;

    public ShippingAclAdapter(LegacyShippingClient legacyClient) {
        this.legacyClient = legacyClient;
    }

    @Override
    public Shipment shipOrder(UUID orderId, String address) {
        // 1. Call the external system (Messy world)
        LegacyShippingResponse response = legacyClient.createShipmentInLegacySystem(orderId.toString(), address);

        // 2. Translate/Map to Domain (Clean world) -> THIS IS THE ANTI-CORRUPTION LAYER
        return mapToDomain(response);
    }

    private Shipment mapToDomain(LegacyShippingResponse response) {
        Shipment.ShipmentStatus status;

        // Translate status codes to Domain Enum
        if (response.STATUS_CODE == 99) {
            status = Shipment.ShipmentStatus.SHIPPED;
        } else {
            status = Shipment.ShipmentStatus.FAILED;
        }

        return new Shipment(
                UUID.fromString(response.XML_SHIP_ID),
                response.TRACK_REF_NO,
                status);
    }
}
