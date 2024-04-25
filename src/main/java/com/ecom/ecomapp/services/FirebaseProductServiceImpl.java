package com.ecom.ecomapp.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecom.ecomapp.config.FirebaseConfig;
import com.ecom.ecomapp.model.ProductDto;
import com.ecom.ecomapp.repositories.ProductFirestoreRepositoryImpl;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class FirebaseProductServiceImpl implements IFirebaseService<String, ProductDto> {

	private final RestTemplate restTemplate;

	private Firestore firestore;

	private final ProductFirestoreRepositoryImpl productRepository;

//	@Override
//	public void createUser(CredentialPayload payload) {
//		UserRecord.CreateRequest uc = new UserRecord.CreateRequest();
//		uc.setEmail(payload.getEmail());
//		uc.setPassword(payload.getPassword());
//		uc.setDisplayName("test");
//
//		FirebaseAuth fba = FirebaseAuth.getInstance(FirebaseApp.getInstance());
//		try {
//			fba.createUser(uc);
//			log.debug("User with email {} has been created successfully!", payload.getEmail());
//		} catch (FirebaseAuthException e) {
//			e.printStackTrace();
//		}
//	}
//

	@Override
	public List<ProductDto> getAll() {
		List<QueryDocumentSnapshot> documents = productRepository.getAll();
		List<ProductDto> productList = documents.stream().map(document -> {
			return new ProductDto().name(document.get("pname").toString()).price(document.get("price").toString());
		}).collect(Collectors.toList());
		return productList;
	}

	@Override
	public ProductDto create(ProductDto productDto) {
		Map<String, Object> data = new HashMap<>();
		data.put("pname", productDto.getName());
		data.put("price", productDto.getPrice());
		data.put("isActive", productDto.getIsActive());
		data.put("isDeleted", false);
		data.put("isPrincipal", productDto.getIsPrincipal());
		data.put("description", productDto.getDescription());
		data.put("mainImage", productDto.getMainImage());
		data.put("subImages", productDto.getSubImages());
		String docId = productRepository.add(data);
		productDto.setId(docId);
		log.debug("Product with docId '{}' has been added!", docId);
		return productDto;
	}

	@Override
	public void delete(String docId) {
		Map<String, Object> productUpdate = new HashMap<>();
		productUpdate.put("isDeleted", true);
		productUpdate.put("updated", Timestamp.of(new Date()));
		productRepository.update(docId, productUpdate);
		log.warn("Product with docId '{}' has been marked as Deleted!", docId);
	}

	@Override
	public void deletePermanent(String docId) {
		productRepository.deletePermanently(docId);
		log.warn("Product with docId '{}' has been Permanenetly Deleted!", docId);
	}

	@Override
	public ProductDto getById(String docId) {
		DocumentSnapshot docSnapshot = productRepository.get(docId);
		List<String> subImages = (List<String>) docSnapshot.get("subImages");
		ProductDto productDto = new ProductDto().name(docSnapshot.getString("pname")).price(docSnapshot.getString("price"))
				.description(docSnapshot.getString("description")).mainImage(docSnapshot.getString("mainImage"))
				.isActive(docSnapshot.getBoolean("isActive")).isDeleted(docSnapshot.getBoolean("isDeleted"))
				.isPrincipal(docSnapshot.getBoolean("isPrincipal")).subImages(subImages);
		log.debug("Retrieve Document with docId '{}': {}", docId, productDto);
		return productDto;
	}

	@Override
	public ProductDto get(ProductDto e) {
		return getById(e.getId());
	}

}
