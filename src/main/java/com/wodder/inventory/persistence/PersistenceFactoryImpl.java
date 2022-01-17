package com.wodder.inventory.persistence;

public class PersistenceFactoryImpl implements PersistenceFactory {
	private final InventoryItemRepository inventoryItemStorage = new InMemoryInventoryItemStorage();

	@Override
	public InventoryItemRepository getInventoryDataStore() {
		return inventoryItemStorage;
	}
}
