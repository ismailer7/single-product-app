package com.ecom.ecomapp.services;

import java.util.List;

import com.ecom.ecomapp.model.CredentialPayload;
import com.ecom.ecomapp.model.ProductDto;
import com.google.cloud.firestore.QueryDocumentSnapshot;

public interface IFirebaseService {

	void createUser(CredentialPayload payload);
	
	String auth(CredentialPayload payload);
	
	List<QueryDocumentSnapshot> getProducts();
	
	void addProduct(ProductDto productDto);
	
	void delete(String docId);
	
}
