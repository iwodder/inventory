package com.wodder.inventory.persistence;

public class PersistenceFactoryImpl implements PersistenceFactory {
	private final InventoryItemStorage inventoryItemStorage = new InMemoryInventoryItemStorage();

	@Override
	public InventoryItemStorage getInventoryDataStore() {
		return inventoryItemStorage;
	}
}
