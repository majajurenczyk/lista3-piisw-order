package com.service.order.historyadapter;

import com.service.order.domain.Order;
import com.service.order.historyadapter.model.OrderHistoryDto;
import com.service.order.mapper.OrderHistoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class OrderHistoryAdapter implements OrderHistoryAPI {

    private final OrderHistoryMapper mapper;
    private final HttpHeaders httpHeaders;

    private static final String ORDER_HISTORY_BASE_URL = "http://localhost:8099";
    private static final String ADD_ORDER_HISTORY_URL = "/orders-history";

    @Autowired
    public OrderHistoryAdapter(OrderHistoryMapper mapper) {
        this.mapper = mapper;
        httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", "application/json");
    }

    @Override
    public void updateOrderHistoryDeliveryByOrderId(Long orderId, String deliveryStatus) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OrderHistoryDto> result = restTemplate.exchange(
                buildUpdateDeliveryUrl(orderId, deliveryStatus),
                HttpMethod.POST,
                null,
                OrderHistoryDto.class);
        log.info(String.valueOf(result.getBody()));
    }

    @Override
    public void addOrderHistory(Order order) {
        OrderHistoryDto orderHistoryDto = mapper.orderToOrderHistoryDto(order);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OrderHistoryDto> result = restTemplate.exchange(
                ORDER_HISTORY_BASE_URL + ADD_ORDER_HISTORY_URL,
                HttpMethod.POST,
                new HttpEntity<>(orderHistoryDto, httpHeaders),
                OrderHistoryDto.class);
        log.info(String.valueOf(result.getBody()));
    }

    private String buildUpdateDeliveryUrl(Long orderId, String status) {
        return ORDER_HISTORY_BASE_URL + "/orders-history/"
                + orderId
                + "/delivery?deliveryStatus=" + status;
    }
}
