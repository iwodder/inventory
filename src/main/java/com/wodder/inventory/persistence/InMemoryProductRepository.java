package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.util.*;
import java.util.stream.*;

final class InMemoryProductRepository extends InMemoryRepository<Product, ProductId> implements ProductRepository {

	InMemoryProductRepository() {
	}

	@Override
	public List<Product> loadActiveItems() {
		return items.stream()
				.filter(Product::isActive)
				.map(Product::new)
				.collect(Collectors.toList());
	}
}
