package com.service.order.service;

import com.service.order.domain.DeliveryStatus;
import com.service.order.domain.Order;
import com.service.order.exception.OrderNotFoundException;

public interface OrderServiceAPI {
    Order addNewOrder(Order newOrder);

    Order updateOrderDelivery(Long orderId, DeliveryStatus deliveryStatus) throws OrderNotFoundException;
}
