package com.wodder.inventory.persistence;

public class PersistenceFactoryImpl implements PersistenceFactory {
	private InventoryItems inventoryItems = new InMemoryInventoryItems();

	@Override
	public InventoryItems getInventoryDataStore() {
		return inventoryItems;
	}
}
