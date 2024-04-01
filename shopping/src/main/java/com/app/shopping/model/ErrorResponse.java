package com.app.shopping.model;

public class ErrorResponse {
	private String description;
	private int statusCode;
	
	
	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ErrorResponse(String description, int statusCode) {
		super();
		this.description = description;
		this.statusCode = statusCode;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
