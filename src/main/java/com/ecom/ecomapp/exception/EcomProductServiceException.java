package com.ecom.ecomapp.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EcomProductServiceException extends RuntimeException {

	private static Logger logger = LogManager.getLogger(FirebaseAuthenticationException.class.toString());
	/**
	 * 
	 */
	private static final long serialVersionUID = -7107879666540063217L;

	private String message;

	public EcomProductServiceException() {
		super();
	}

	public EcomProductServiceException(String message) {
		super();
		this.message = message;
		logger.error(this.message);
	}

	public String getMessage() {
		return this.message;
	}

}
