package com.ecom.ecomapp.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class PaymentServiceExceptionHandler {
	
//	@ExceptionHandler(CustomerCreationException.class)
//	public ResponseEntity<ErrorResponse> handleCustomerCreationException(CustomerCreationException ex) {
//		var response = ErrorResponse.builder().timestamp(OffsetDateTime.now()).message(ex.getMsg()).errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
//		return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
}
