package com.service.order.exception;

public class DeliveryNotFoundForOrderException extends Exception {
    public DeliveryNotFoundForOrderException(String message){
        super(message);
    }
}
