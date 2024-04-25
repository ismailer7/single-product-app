package com.ecom.ecomapp.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecomapp.EcomAppConstants;
import com.ecom.ecomapp.api.controller.UserApi;
import com.ecom.ecomapp.model.CredentialPayload;
import com.ecom.ecomapp.model.RegisterPayload;
import com.ecom.ecomapp.services.FirebaseUserServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(EcomAppConstants.API_VERSION)
public class AuthController implements UserApi {

	private final FirebaseUserServiceImpl firebaseUserServiceImpl;

	@Override
	public ResponseEntity<String> auth(@Valid @RequestBody CredentialPayload payload) {
		return new ResponseEntity<String>(firebaseUserServiceImpl.auth(payload), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> create(@Valid @RequestBody RegisterPayload credentialPayload) {
		firebaseUserServiceImpl.register(credentialPayload);
		return new ResponseEntity<String>("User Created!", HttpStatus.OK);
	}
}
