package com.ecom.ecomapp.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecom.ecomapp.config.FirebaseConfig;
import com.ecom.ecomapp.model.CredentialPayload;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class FirebaseServiceImpl implements IFirebaseService {

	private FirebaseConfig firebaseConfig;

	private final RestTemplate restTemplate;

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

}
