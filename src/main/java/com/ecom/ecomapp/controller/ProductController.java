package com.ecom.ecomapp.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecomapp.EcomAppConstants;
import com.ecom.ecomapp.api.controller.ProductApi;
import com.ecom.ecomapp.exception.EcomProductServiceException;
import com.ecom.ecomapp.exception.ErrorResponse;
import com.ecom.ecomapp.model.ProductDto;
import com.ecom.ecomapp.services.FirebaseProductServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(EcomAppConstants.API_VERSION)
public class ProductController implements ProductApi {

	private FirebaseProductServiceImpl firebaseProductService;

	@Override
	public ResponseEntity<String> deleteProduct(@Valid @RequestParam(value = "docId", required = false) String docId) {
		firebaseProductService.delete(docId);
		return new ResponseEntity<String>(String.format("Product with docId '%s' Deleted!", docId), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDto productDto) {
		ProductDto product = firebaseProductService.create(productDto);
		return new ResponseEntity<String>(product.getId(), HttpStatus.OK);
	}

	@ExceptionHandler(value = EcomProductServiceException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleProductOperationException(EcomProductServiceException ex) {
		return ErrorResponse.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage())
				.details(ex.getStackTrace().toString()).build();
	}
}
