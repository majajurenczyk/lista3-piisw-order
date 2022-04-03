package com.service.order.api.message;

import com.service.order.domain.Product;
import lombok.Value;

@Value
public class OrderItemRequest {
    Product product;
    int quantity;
}
