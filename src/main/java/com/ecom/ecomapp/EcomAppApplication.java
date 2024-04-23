package com.ecom.ecomapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.google.firebase.auth.FirebaseAuthException;

@SpringBootApplication
@ComponentScan(basePackages = EcomAppConstants.BASE_PACKAGE)
public class EcomAppApplication {

	public static void main(String[] args) throws FirebaseAuthException {
		SpringApplication.run(EcomAppApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate(SimpleClientHttpRequestFactory requestFactory) {
		return new RestTemplate(requestFactory);
	}

	@Bean
	SimpleClientHttpRequestFactory requestFactory() {
		var requestFactory = new SimpleClientHttpRequestFactory();
		return requestFactory;
	}
}
