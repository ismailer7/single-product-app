package com.ecom.ecomapp.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import lombok.Data;

@Configuration
@Data
public class FirebaseConfig {

	@Value("${firebase.service.account}")
	String firebaseAppresourceFile;
	
	@Value("${firebase.apiKey}")
	String firebaseApiKey;
	
	@Value("${firebase.api.endpoint}")
	String firbaseEndpoint;

	@PostConstruct
	public void initializeFirebaseApp() throws InterruptedException, ExecutionException {
		FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream(firebaseAppresourceFile);
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

			FirebaseApp.initializeApp(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
