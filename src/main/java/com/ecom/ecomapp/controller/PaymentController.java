package com.ecom.ecomapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecomapp.EcomAppConstants;
import com.ecom.ecomapp.api.controller.PaymentApi;
import com.ecom.ecomapp.exception.EcomPaymentGatewayException;
import com.ecom.ecomapp.model.PaymentPayload;
import com.ecom.ecomapp.services.IStripePaymentService;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(EcomAppConstants.API_VERSION)
public class PaymentController implements PaymentApi {

	private final IStripePaymentService stripeService;

	@Override
	public ResponseEntity<String> createPI(@RequestBody PaymentPayload paymentPayload) {
		var pi = stripeService.createPaymentIntent(paymentPayload);
		return new ResponseEntity<String>(pi, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> confirmPI(@RequestParam String paymentIntent) {
		PaymentIntent pi = stripeService.confirmPaymentIntent(paymentIntent);
		return ResponseEntity.ok("Payment successful with payment intent ID: " + pi.getId());
	}

	@Override
	public ResponseEntity<Customer> addCustomer(@RequestParam String name, @RequestParam String email) {
		Customer customer = stripeService.createCustomer(name, email);
		return ResponseEntity.ok(customer);
	}

	@GetMapping("/success")
	public String success() {
		return "success";
	}

	@ExceptionHandler(value = EcomPaymentGatewayException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> handlePaymentGatewayException(EcomPaymentGatewayException ex) {
		return ResponseEntity.internalServerError().body(ex.getMessage());
	}

}