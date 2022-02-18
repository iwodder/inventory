package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

public class PersistenceFactoryImpl implements PersistenceFactory {
	private static final ProductRepository inventoryItemStorage = new InMemoryProductRepository();
	private static final InMemoryCategoryRepository categoryRepository = new InMemoryCategoryRepository();
	private static final InMemoryLocationRepository locationRepository = new InMemoryLocationRepository();

	@Override
	public ProductRepository getInventoryDataStore() {
		return inventoryItemStorage;
	}

	@Override
	public <T extends Entity> Repository<T> getRepository(Class<T> clazz) {
		if (clazz.isAssignableFrom(Category.class)) {
			@SuppressWarnings("unchecked")
			Repository<T> c =  (Repository<T>) categoryRepository;
			return c;
		} else if (clazz.isAssignableFrom(Location.class)) {
			@SuppressWarnings("unchecked")
			Repository<T> c =  (Repository<T>) locationRepository;
			return c;
		}
		return null;
	}
}
