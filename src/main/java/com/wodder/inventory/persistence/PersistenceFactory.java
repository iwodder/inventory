package com.wodder.inventory.persistence;

public interface PersistenceFactory {

	InventoryItemStorage getInventoryDataStore();
}
