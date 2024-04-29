package com.ecom.ecomapp.services;

import com.ecom.ecomapp.model.PaymentPayload;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;

public interface IStripePaymentService {

	Customer createCustomer(String name, String email);

	String createPaymentIntent(PaymentPayload paymentPayload);

	PaymentIntent confirmPaymentIntent(String pi);

}
