package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

public class PersistenceFactoryImpl implements PersistenceFactory {
	private static final ProductRepository productRepository = new InMemoryProductRepository();
	private static final InMemoryCategoryRepository categoryRepository = new InMemoryCategoryRepository();
	private static final InMemoryLocationRepository locationRepository = new InMemoryLocationRepository();
	private static final InMemoryInventoryRepository inventoryRepository = new InMemoryInventoryRepository();

	@Override
	public ProductRepository getInventoryDataStore() {
		return productRepository;
	}

	@Override
	public <T extends Entity<U>, U> Repository<T, U> getRepository(Class<T> clazz) {
		if (clazz.isAssignableFrom(Category.class)) {
			@SuppressWarnings("unchecked")
			Repository<T, U> c =  (Repository<T, U>) categoryRepository;
			return c;
		} else if (clazz.isAssignableFrom(Location.class)) {
			@SuppressWarnings("unchecked")
			Repository<T, U> c =  (Repository<T, U>) locationRepository;
			return c;
		} else if (clazz.isAssignableFrom(Inventory.class)) {
			@SuppressWarnings("unchecked")
			Repository<T, U> c =  (Repository<T, U>) inventoryRepository;
			return c;
		} else if (clazz.isAssignableFrom(Product.class)) {
			@SuppressWarnings("unchecked")
			Repository<T, U> c =  (Repository<T, U>) productRepository;
			return c;
		}
		return null;
	}
}
