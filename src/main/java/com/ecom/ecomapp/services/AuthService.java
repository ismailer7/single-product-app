package com.ecom.ecomapp.services;

import com.ecom.ecomapp.model.CredentialPayload;
import com.ecom.ecomapp.model.RegisterPayload;

public interface AuthService {

	String auth(CredentialPayload payload);
	
	void register(RegisterPayload payload);
	
}
