package com.ecom.ecomapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecomapp.EcomAppConstants;
import com.ecom.ecomapp.services.IStripePaymentService;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(EcomAppConstants.API_VERSION)
public class PaymentController {

	private final IStripePaymentService stripeService;

	@GetMapping("/create")
	public ResponseEntity<String> create() {
		var pi = stripeService.createPaymentIntent();
		return new ResponseEntity<String>(pi, HttpStatus.OK);
	}

	@GetMapping("/confirm")
	public ResponseEntity<PaymentIntent> confirm(@RequestParam String paymentIntent) {
		PaymentIntent pi = stripeService.confirmPaymentIntent(paymentIntent);
		return new ResponseEntity<PaymentIntent>(pi, HttpStatus.OK);
	}
	
	@GetMapping("/customer")
	public ResponseEntity<Customer> addCustomer(@RequestParam String name, @RequestParam String email) {
		Customer customer = stripeService.createCustomer(name, email);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@GetMapping("/success")
	public String success() {
		return "success";
	}

}