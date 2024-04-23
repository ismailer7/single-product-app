package com.ecom.ecomapp.services;

import com.ecom.ecomapp.model.CredentialPayload;

public interface IFirebaseService {

	void createUser(CredentialPayload payload);
	
	String auth(CredentialPayload payload);
	
}
