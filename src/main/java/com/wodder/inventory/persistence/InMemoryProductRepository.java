package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

final class InMemoryProductRepository implements ProductRepository {
	private static final AtomicLong itemId = new AtomicLong(0);
	private final Map<Long, Product> db;
	private final Map<Product, InventoryCount> counts;

	InMemoryProductRepository() {
		db = new HashMap<>();
		counts = new HashMap<>();
	}

	@Override
	public Optional<Product> loadItem(Long id) {
		if (db.containsKey(id)) {
			return Optional.of(new Product(db.get(id)));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Product> updateItem(Product item) {
		if (db.containsKey(item.getId())) {
			Product updatedItem = new Product(item);
			db.replace(item.getId(), updatedItem);
			return Optional.of(new Product(updatedItem));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Product> saveItem(Product item) {
		Long id = InMemoryProductRepository.itemId.getAndIncrement();
		Product newItem = new Product(item);
		newItem.setId(id);
		db.put(id, newItem);
		counts.put(newItem, new InventoryCount(newItem.getId(), newItem.getName(), newItem.getCategory(), item.getLocation()));
		return Optional.of(new Product(newItem));
	}

	@Override
	public boolean deleteItem(Long itemId) {
		if (itemId != null) {
			db.remove(itemId);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Product> loadAllItems() {
		return db.values().stream()
				.map(Product::new)
				.collect(Collectors.toList());
	}

	@Override
	public List<Product> loadActiveItems() {
		return db.values().stream()
				.filter(Product::isActive)
				.map(Product::new)
				.collect(Collectors.toList());
	}
}
