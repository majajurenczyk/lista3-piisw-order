package com.service.order.mapper;

import com.service.order.domain.Order;
import com.service.order.historyadapter.model.OrderHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderHistoryMapper {

    @Mapping(target="orderId", source="order.id")
    @Mapping(target="customerName", source="order.customerName")
    @Mapping(target="courierName", expression="java(order.getDelivery().getCourierName())")
    @Mapping(target="deliveryStatus", expression="java(order.getDelivery().getStatus().name())")
    @Mapping(target="productNames", expression = "java(order.concatenateItemsNames())")
    @Mapping(target="totalPrice", expression = "java(order.calculateTotalPrice())")
    OrderHistoryDto orderToOrderHistoryDto(Order order);
}
