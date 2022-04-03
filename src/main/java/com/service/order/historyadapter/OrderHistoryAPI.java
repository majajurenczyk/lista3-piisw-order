package com.service.order.historyadapter;

import com.service.order.domain.Order;

public interface OrderHistoryAPI {
    void updateOrderHistoryDeliveryByOrderId(Long orderId, String deliveryStatus);
    void addOrderHistory(Order order);
}
