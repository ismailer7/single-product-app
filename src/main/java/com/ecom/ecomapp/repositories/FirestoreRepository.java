package com.ecom.ecomapp.repositories;

import java.util.List;
import java.util.Map;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

public interface FirestoreRepository {

	String add(Map<String, Object> payload) throws Exception;

	DocumentSnapshot get(String docId) throws Exception;

	List<QueryDocumentSnapshot> getAll() throws Exception;

	void update(String docId, Map<String, Object> updatePayload);
	
	void deletePermanently(String docId);

}
