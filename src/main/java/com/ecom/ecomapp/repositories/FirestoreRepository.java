package com.ecom.ecomapp.repositories;

import java.util.List;
import java.util.Map;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

public interface FirestoreRepository {

	String add(Map<String, Object> payload);

	DocumentSnapshot get(String docId);

	List<QueryDocumentSnapshot> getAll();

	void update(String docId, Map<String, Object> updatePayload);
	
	void deletePermanently(String docId);

}
