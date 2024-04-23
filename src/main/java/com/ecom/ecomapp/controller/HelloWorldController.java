package com.ecom.ecomapp.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.ecom.ecomapp.EcomAppConstants;
import com.ecom.ecomapp.api.controller.GreetingApi;
import com.ecom.ecomapp.api.controller.UserApi;
import com.ecom.ecomapp.model.CredentialPayload;
import com.ecom.ecomapp.services.FirebaseServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(EcomAppConstants.API_VERSION)
public class HelloWorldController implements GreetingApi, UserApi {

	private FirebaseServiceImpl firebaseService;

	@Override
	public ResponseEntity<String> greeting() {
		return new ResponseEntity<String>("Hello Developer!", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> auth(@Valid @RequestBody CredentialPayload credentialPayload) {
		return new ResponseEntity<String>(firebaseService.auth(credentialPayload), HttpStatus.OK);
	}

	@Override
	public Optional<NativeWebRequest> getRequest() {
		return GreetingApi.super.getRequest();
	}

	@Override
	public ResponseEntity<String> create(@Valid @RequestBody CredentialPayload credentialPayload) {
		firebaseService.createUser(credentialPayload);
		return new ResponseEntity<String>("User Created!", HttpStatus.OK);
	}

}
