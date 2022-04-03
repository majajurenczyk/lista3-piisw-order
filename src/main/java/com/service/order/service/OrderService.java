package com.service.order.service;

import com.service.order.domain.DeliveryStatus;
import com.service.order.domain.Order;
import com.service.order.exception.OrderNotFoundException;
import com.service.order.historyadapter.OrderHistoryAPI;
import com.service.order.repository.OrderDAO;
import com.service.order.repository.ProductDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService implements OrderServiceAPI {

    OrderDAO orderDAO;
    ProductDAO productDAO;
    OrderHistoryAPI orderHistoryAPI;

    @Autowired
    public OrderService(OrderDAO orderDAO, ProductDAO productDAO, OrderHistoryAPI orderHistoryAPI) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
        this.orderHistoryAPI = orderHistoryAPI;
    }

    @Override
    public Order addNewOrder(Order newOrder) {
        newOrder.getItems().forEach(item -> productDAO.save(item.getProduct()));
        Order result = orderDAO.save(newOrder);
        sendOrderToOrderHistoryMicroservice(result);
        return result;
    }

    @Override
    public Order updateOrderDelivery(Long orderId, DeliveryStatus deliveryStatus) throws OrderNotFoundException {
        Order orderToUpdateDeliveryStatus = orderDAO.findById(orderId).orElse(null);
        if (orderToUpdateDeliveryStatus == null)
            throw new OrderNotFoundException("Order with id: " + orderId + "not found.");
        else {
            orderToUpdateDeliveryStatus.getDelivery().setStatus(deliveryStatus);
            Order result = orderDAO.save(orderToUpdateDeliveryStatus);
            updateDeliveryStatusInOrderHistoryMicroservice(orderId, deliveryStatus.name());
            return result;
        }
    }

    private void sendOrderToOrderHistoryMicroservice(Order orderHistory) {
        try {
            orderHistoryAPI.addOrderHistory(orderHistory);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error occurred when trying to send order with id: "
                    + orderHistory.getId() + "to order history microservice");
        }
    }

    private void updateDeliveryStatusInOrderHistoryMicroservice(Long orderId, String status) {
        try {
            orderHistoryAPI.updateOrderHistoryDeliveryByOrderId(orderId, status);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error occurred when trying update delivery status for order with id: "
                    + orderId + "in order history microservice");
        }
    }
}
