package com.service.order.mapper;

import com.service.order.api.message.DeliveryRequest;
import com.service.order.api.message.OrderItemRequest;
import com.service.order.api.message.OrderRequest;
import com.service.order.domain.Delivery;
import com.service.order.domain.Order;
import com.service.order.domain.OrderItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target="status", source="deliveryRequest.deliveryStatus")
    @Mapping(target="courierName", source="deliveryRequest.courierName")
    Delivery deliveryRequestToDelivery(DeliveryRequest deliveryRequest);

    @Mapping(target="product", source="orderItemRequest.product")
    @Mapping(target="quantity", source="orderItemRequest.quantity")
    OrderItem orderItemRequestToOrderItem(OrderItemRequest orderItemRequest);

    @Mapping(target="customerName", source="orderRequest.customerName")
    @Mapping(target="delivery", source="orderRequest.delivery")
    @Mapping(target="items", source="orderRequest.items")
    Order orderRequestToOrder(OrderRequest orderRequest);

    @AfterMapping
    default void setOrderIds(@MappingTarget Order order){
        for (OrderItem o: order.getItems()) {
            o.setOrder(order);
        }
    }
}
