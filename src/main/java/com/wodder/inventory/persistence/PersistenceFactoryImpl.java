package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

public class PersistenceFactoryImpl implements PersistenceFactory {
	private final InventoryItemRepository inventoryItemStorage = new InMemoryInventoryItemStorage();

	@Override
	public InventoryItemRepository getInventoryDataStore() {
		return inventoryItemStorage;
	}

	@Override
	public <T extends Entity> Repository<T> getRepository(Class<T> clazz) {
		if (clazz.isAssignableFrom(Category.class)) {
			return (Repository<T>) new InMemoryCategoryRepository();
		} else if (clazz.isAssignableFrom(Location.class)) {
			return (Repository<T>) new InMemoryLocationRepository();
		}
		return null;
	}
}
