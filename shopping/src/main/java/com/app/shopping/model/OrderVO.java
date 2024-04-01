package com.app.shopping.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderVO {
	private Long orderId;
	private Double amount;
	private String date;
	private String coupon;
	private String transactionId;
	private String status;

	public OrderVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderVO(Long orderId, Double amount, String date, String coupon, String transactionId, String status) {
		super();
		this.orderId = orderId;
		this.amount = amount;
		this.date = date;
		this.coupon = coupon;
		this.transactionId = transactionId;
		this.status = status;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
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
	
	

}
