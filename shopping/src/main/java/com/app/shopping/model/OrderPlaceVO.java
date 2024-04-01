package com.app.shopping.model;





public class OrderPlaceVO {

    private Long orderId;
    private Long userId;
    private int quantity;
    private Double amount;
    private String coupon;
	public OrderPlaceVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderPlaceVO(Long orderId, Long userId, int quantity, Double amount, String coupon) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.quantity = quantity;
		this.amount = amount;
		this.coupon = coupon;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCoupen() {
		return coupon;
	}
	public void setCode(String coupon) {
		this.coupon = coupon;
	}
    
	
}
