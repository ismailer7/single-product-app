package com.ecom.ecomapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecomapp.EcomAppConstants;

@RestController
@RequestMapping(EcomAppConstants.API_VERSION)
public class HelloWorldController {
	
	@GetMapping("/greeting")
	public String greeting() {
		return "Hello World!";
	}
	
}
