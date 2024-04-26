package com.ecom.ecomapp.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

	private int errorCode;

	private String message;

	private String details;

}
