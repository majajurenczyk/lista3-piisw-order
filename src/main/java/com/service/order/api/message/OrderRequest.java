package com.service.order.api.message;

import lombok.*;

import java.util.List;

@Value
public class OrderRequest {
    String customerName;
    List<OrderItemRequest> items;
    DeliveryRequest delivery;
}
