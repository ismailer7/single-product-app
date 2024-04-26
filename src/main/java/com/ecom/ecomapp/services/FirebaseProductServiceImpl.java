package com.ecom.ecomapp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecom.ecomapp.exception.EcomProductServiceException;
import com.ecom.ecomapp.model.ProductDto;
import com.ecom.ecomapp.repositories.ProductFirestoreRepositoryImpl;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class FirebaseProductServiceImpl implements IFirebaseService<String, ProductDto> {

	private final ProductFirestoreRepositoryImpl productRepository;

	@Override
	public List<ProductDto> getAll() {
		List<QueryDocumentSnapshot> documents = new ArrayList<QueryDocumentSnapshot>();
		try {
			documents = productRepository.getAll();
		} catch (Exception e) {
			throw new EcomProductServiceException(e.getMessage());
		}
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
		data.put("created", Timestamp.of(new Date()));
		data.put("updated", Timestamp.of(new Date()));
		try {
			String docId = productRepository.add(data);
			productDto.setId(docId);
			log.debug("Product with docId '{}' has been added!", docId);
		} catch (Exception e) {
			throw new EcomProductServiceException(e.getMessage());
		}
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
		DocumentSnapshot docSnapshot;
		try {
			docSnapshot = productRepository.get(docId);
		} catch (Exception e) {
			throw new EcomProductServiceException(e.getMessage());
		}
		List<String> subImages = (List<String>) docSnapshot.get("subImages");
		ProductDto productDto = new ProductDto().name(docSnapshot.getString("pname"))
				.price(docSnapshot.getString("price")).description(docSnapshot.getString("description"))
				.mainImage(docSnapshot.getString("mainImage")).isActive(docSnapshot.getBoolean("isActive"))
				.isDeleted(docSnapshot.getBoolean("isDeleted")).isPrincipal(docSnapshot.getBoolean("isPrincipal"))
				.subImages(subImages);
		log.debug("Retrieve Document with docId '{}': {}", docId, productDto);
		return productDto;
	}

	@Override
	public ProductDto get(ProductDto e) {
		return getById(e.getId());
	}

	@Override
	public ProductDto main() {
		DocumentSnapshot documentSnapshot = null;
		ProductDto product = new ProductDto();
		try {
			documentSnapshot = productRepository.getMainProduct();
			if (documentSnapshot != null) {
				List<String> subImages = (List<String>) documentSnapshot.get("subImages");
				product.name(documentSnapshot.getString("pname")).price(documentSnapshot.getString("price"))
						.description(documentSnapshot.getString("description"))
						.mainImage(documentSnapshot.getString("mainImage")).isDeleted(false).isPrincipal(true)
						.isActive(true).subImages(subImages);
			} else {
				throw new Exception("No Main Product is Available in Firestore");
			}
		} catch (Exception e) {
			throw new EcomProductServiceException(e.getMessage());
		}
		return product;
	}

}
