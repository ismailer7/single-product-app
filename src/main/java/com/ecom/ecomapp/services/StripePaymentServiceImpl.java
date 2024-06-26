package com.ecom.ecomapp.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecom.ecomapp.exception.EcomPaymentGatewayException;
import com.ecom.ecomapp.model.PaymentPayload;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StripePaymentServiceImpl implements IStripePaymentService {

	@Value("${stripe.apiKey}")
	private String apiKey;

	@PostConstruct
	private void initStripeApi() {
		Stripe.apiKey = this.apiKey;
	}

	@Override
	public String createPaymentIntent(PaymentPayload paymentPayload) {
		try {
			PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
					.setAmount(Long.valueOf(paymentPayload.getAmount())).setCurrency(paymentPayload.getCurrenrcy())
					.setDescription(paymentPayload.getDescription()).setCustomer(paymentPayload.getCustomer())
					.setAutomaticPaymentMethods(
							PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
					.build();
			PaymentIntent paymentIntent = PaymentIntent.create(params);
			var pi = paymentIntent.getId();
			return pi;
		} catch (StripeException e) {
			log.error("Error Creating Payment Intent: {}!", e.getMessage());
			throw new EcomPaymentGatewayException(e.getMessage());
		}
	}

	@Override
	public PaymentIntent confirmPaymentIntent(String pi) {
		PaymentIntent resource;
		try {
			resource = PaymentIntent.retrieve(pi);
			PaymentIntentConfirmParams params = PaymentIntentConfirmParams.builder().setPaymentMethod("pm_card_visa")
					.setReturnUrl("http://localhost:8080/v1/api/success").build();
			PaymentIntent paymentIntent = resource.confirm(params);
			paymentIntent.getRawJsonObject().addProperty("test", "test");
			return paymentIntent;
		} catch (StripeException e) {
			log.error("Error Confirming Payment Intent {}.", e.getMessage());
			throw new EcomPaymentGatewayException(e.getMessage());
		}
	}

	@Override
	public Customer createCustomer(String name, String email) {
		try {
			CustomerCreateParams params = CustomerCreateParams.builder().setName(name).setEmail(email).build();
			Customer customer = Customer.create(params);
			return customer;
		} catch (StripeException e) {
			log.error("Error Creating Customer {}", e.getMessage());
			throw new EcomPaymentGatewayException(e.getMessage());
		}
	}

}
