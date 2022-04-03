package com.service.order.historyadapter.model;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class OrderHistoryDto {
    Long orderId;

    String customerName;

    String courierName;

    String deliveryStatus;

    String productNames;

    BigDecimal totalPrice;
}
