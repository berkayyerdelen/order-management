package com.klm.order_management.order.domain.shipping;

import java.util.UUID;

public class Shipment {
    private final UUID id;
    private final String trackingNumber;
    private final ShipmentStatus status;

    public Shipment(UUID id, String trackingNumber, ShipmentStatus status) {
        this.id = id;
        this.trackingNumber = trackingNumber;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    // Clean Domain Enum
    public enum ShipmentStatus {
        PENDING,
        SHIPPED,
        DELIVERED,
        FAILED
    }
}
