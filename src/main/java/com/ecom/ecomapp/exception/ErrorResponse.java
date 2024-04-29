package com.ecom.ecomapp.exception;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime timestamp;
	
	private int errorCode;

	private String message;

	private String details;

}
