package com.app.shopping.exception;



public class InvalidCouponException extends Exception {
    public InvalidCouponException() {
        super("Invalid coupon");
    }
}