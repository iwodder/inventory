package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.util.*;

public interface ProductRepository {

	Optional<Product> loadItem(Long id);

	Optional<Product> updateItem(Product item);

	Optional<Product> saveItem(Product item);

	boolean deleteItem(Long itemId);

	List<Product> loadAllItems();

	List<Product> loadActiveItems();
}
