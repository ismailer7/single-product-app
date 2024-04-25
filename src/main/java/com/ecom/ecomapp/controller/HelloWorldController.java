package com.ecom.ecomapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecomapp.EcomAppConstants;
import com.ecom.ecomapp.api.controller.GreetingApi;

@RestController
@RequestMapping(EcomAppConstants.API_VERSION)
public class HelloWorldController implements GreetingApi {

	@Override
	public ResponseEntity<String> greeting() {
		return new ResponseEntity<String>("Hello Developer!", HttpStatus.OK);
	}

}
