package com.ecom.ecomapp.services;

import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;

public interface IStripePaymentService {

	Customer createCustomer(String name, String email);

	String createPaymentIntent();

	PaymentIntent confirmPaymentIntent(String pi);

}
