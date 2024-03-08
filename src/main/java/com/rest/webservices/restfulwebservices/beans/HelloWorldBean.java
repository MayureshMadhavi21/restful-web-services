package com.rest.webservices.restfulwebservices.beans;

public class HelloWorldBean {
   
	private String message;

	public HelloWorldBean(String message) {
		super();
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "HelloWorldBean [message=" + message + "]";
	}
}
