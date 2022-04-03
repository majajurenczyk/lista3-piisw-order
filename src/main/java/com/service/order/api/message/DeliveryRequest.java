package com.service.order.api.message;

import com.service.order.domain.DeliveryStatus;
import lombok.Value;

@Value
public class DeliveryRequest {
    DeliveryStatus deliveryStatus;
    String courierName;
}
