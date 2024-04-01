package com.app.shopping.model;



public class InventoryStatusVO {
    private int ordered;
    private Double price;
    private int available;
    
	public InventoryStatusVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InventoryStatusVO(int ordered, Double price, int available) {
		super();
		this.ordered = ordered;
		this.price = price;
		this.available = available;
	}

	public int getOrdered() {
		return ordered;
	}

	public void setOrdered(int ordered) {
		this.ordered = ordered;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}
    

}

