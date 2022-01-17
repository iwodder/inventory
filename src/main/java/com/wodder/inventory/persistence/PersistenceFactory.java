package com.wodder.inventory.persistence;

public interface PersistenceFactory {

	InventoryItemRepository getInventoryDataStore();
}
