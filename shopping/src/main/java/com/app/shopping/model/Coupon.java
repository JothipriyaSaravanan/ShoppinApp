package com.app.shopping.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="coupon")
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;
    private String code;
    private int discountPercentage;
    
	public Coupon() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Coupon(Long id, String code, int discountPercentage) {
		super();
		this.id = id;
		this.code = code;
		this.discountPercentage = discountPercentage;
	}
	
	public Coupon(String code, int discountPercentage) {
        this.code = code;
        this.discountPercentage = discountPercentage;
    }
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
    
    
}
