package com.service.order.api;

import com.service.order.api.message.ErrorResponse;
import com.service.order.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseOrderExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(OrderNotFoundException e){
        return new ResponseEntity<>(new ErrorResponse("404", "Order not found"), HttpStatus.NOT_FOUND);
    }

/*    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        return new ResponseEntity<>(new ErrorResponse("422",
                "Order cannot be processed"), HttpStatus.UNPROCESSABLE_ENTITY);
    }*/
}
