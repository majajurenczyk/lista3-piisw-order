package com.service.order.api.message;

import lombok.Value;

@Value
public class ErrorResponse {
    String statusCode;
    String message;
}
