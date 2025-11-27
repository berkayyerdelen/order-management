package com.klm.order_management.order.infrastructure.shipping;

// Represents the "Messy" external data structure
public class LegacyShippingResponse {
    public String XML_SHIP_ID;
    public String TRACK_REF_NO;
    public int STATUS_CODE; // 99 = Success, -1 = Error
    public String ERR_MSG;
}
