package com.ecom.ecomapp.services;

import java.util.List;

public interface IFirebaseService<S, E> {

	E getById(S docId);
	
	E get(E e);
	
	List<E> getAll();
	
	E create(E entity);
	
	void delete(S docId);
	
	void deletePermanent(S docId);
	
	E main();
	
}
