package com.service.order.api;

import com.service.order.api.message.OrderRequest;
import com.service.order.domain.DeliveryStatus;
import com.service.order.domain.Order;
import com.service.order.exception.DeliveryNotFoundForOrderException;
import com.service.order.exception.OrderNotFoundException;
import com.service.order.mapper.OrderMapper;
import com.service.order.service.OrderServiceAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/orders")
@RestController
@Slf4j
public class OrderController {

    private final OrderServiceAPI orderServiceAPI;
    private final OrderMapper mapper;

    @Autowired
    public OrderController(OrderServiceAPI orderServiceAPI, OrderMapper mapper){
        this.orderServiceAPI = orderServiceAPI;
        this.mapper = mapper;
    }

    @PatchMapping(path = "/{id}/delivery")
    public ResponseEntity<Order> updateOrderDeliveryStatus(@PathVariable("id") Long orderId,
                                                       @RequestParam DeliveryStatus deliveryStatus)
            throws OrderNotFoundException{
        log.info("Requested to change delivery status " + deliveryStatus.name() + "for order with id " + orderId);
        return new ResponseEntity<>(orderServiceAPI.updateOrderDelivery(orderId, deliveryStatus), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> addNewOrder(@RequestBody @NonNull OrderRequest orderToAdd) {
        log.info("Requested to add order for " + orderToAdd.getCustomerName()) ;
        return new ResponseEntity<>(orderServiceAPI.addNewOrder(mapper.orderRequestToOrder(orderToAdd)),
                HttpStatus.CREATED);
    }
}
