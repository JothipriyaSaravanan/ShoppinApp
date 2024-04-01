package com.app.shopping.model;

public class PaymentResponse {
    private Long userId;
    private Long orderId;
    private String transactionId;
    private String status;
    private String description;

    
    public PaymentResponse(Long userId, Long orderId, String transactionId, String description) {
        this.userId = userId;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.description = description;
    }

    public PaymentResponse(Long userId, Long orderId, String transactionId, String status, String description) {
        this.userId = userId;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.status = status;
        this.description = description;
    }

 
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
