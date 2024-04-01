package com.app.shopping.exception;

import org.springframework.http.HttpStatus;

import com.app.shopping.model.PaymentResponse;

public class PaymentException extends RuntimeException {

    private HttpStatus httpStatus;

    public PaymentException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

	public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
