package com.ecom.ecomapp.exception;

public class CustomerCreationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2283078491947567762L;

	private String msg;

	public CustomerCreationException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

}
