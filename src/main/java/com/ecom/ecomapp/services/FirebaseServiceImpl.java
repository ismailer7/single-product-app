package com.ecom.ecomapp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecom.ecomapp.config.FirebaseConfig;
import com.ecom.ecomapp.model.CredentialPayload;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class FirebaseServiceImpl implements IFirebaseService {

	private FirebaseConfig firebaseConfig;

	private final RestTemplate restTemplate;
	
	private Firestore db = FirestoreClient.getFirestore();

	@Override
	public void createUser(CredentialPayload payload) {
		UserRecord.CreateRequest uc = new UserRecord.CreateRequest();
		uc.setEmail(payload.getEmail());
		uc.setPassword(payload.getPassword());
		uc.setDisplayName("test");

		FirebaseAuth fba = FirebaseAuth.getInstance(FirebaseApp.getInstance());
		try {
			fba.createUser(uc);
			log.debug("User with email {} has been created successfully!", payload.getEmail());
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String auth(CredentialPayload payload) {
		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		var request = new HttpEntity<>(payload, headers);
		String response = restTemplate.postForEntity(firebaseConfig.getFirbaseEndpoint(), request, String.class,
				firebaseConfig.getFirebaseApiKey()).getBody();
		log.debug("Receiving Token from firebase {}", response);
		return response;
	}

	@Override
	public List<QueryDocumentSnapshot> getProducts() {
		try {
			ApiFuture<QuerySnapshot> query = db.collection("products").get();
			QuerySnapshot querySnapshot;
			querySnapshot = query.get();
			List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				System.out.println("Product: " + document.getId());
				System.out.println("Name: " + document.getString("pname"));
				System.out.println("Price: " + document.getString("price"));
			}
			return documents;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return new ArrayList<QueryDocumentSnapshot>();
	}

	@Override
	public void addProduct() {
		DocumentReference docRef = db.collection("products").document("productid232");
		// Add document data  with id "alovelace" using a hashmap
		Map<String, Object> data = new HashMap<>();
		data.put("pname", "CBD Gummies");
		data.put("price", "78");
		//asynchronously write data
		ApiFuture<WriteResult> result = docRef.set(data);
		// ...
		// result.get() blocks on response
		try {
			System.out.println("Update time : " + result.get().getUpdateTime());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
