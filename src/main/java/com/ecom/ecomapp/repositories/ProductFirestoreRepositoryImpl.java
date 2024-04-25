package com.ecom.ecomapp.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@AllArgsConstructor
public class ProductFirestoreRepositoryImpl implements FirestoreRepository {

	private static final String TARGET_COLLECTION = "products";

	private Firestore firestore;

	@Override
	public String add(Map<String, Object> data) {
		try {
			ApiFuture<DocumentReference> docRef = firestore.collection(TARGET_COLLECTION).add(data);
			return docRef.get().getId();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return "-1";
	}

	@Override
	public List<QueryDocumentSnapshot> getAll() {
		List<QueryDocumentSnapshot> documents = new ArrayList<QueryDocumentSnapshot>();
		try {
			CollectionReference products = firestore.collection(TARGET_COLLECTION);
			Query query = products.whereEqualTo("isDeleted", false);
			ApiFuture<QuerySnapshot> querySnapshot = query.get();
			documents = querySnapshot.get().getDocuments();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return documents;
	}

	@Override
	public void deletePermanently(String docId) {
		firestore.collection(TARGET_COLLECTION).document(docId).delete();
	}

	@Override
	public DocumentSnapshot get(String docId) {
		DocumentSnapshot productSnapshot = null;
		try {
			ApiFuture<DocumentSnapshot> product = firestore.collection(TARGET_COLLECTION).document(docId).get();
			productSnapshot = product.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productSnapshot;
	}

	@Override
	public void update(String docId, Map<String, Object> updatePayload) {
		DocumentReference product = firestore.collection(TARGET_COLLECTION).document(docId);
		product.set(updatePayload);
	}

}
