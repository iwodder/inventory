package com.wodder.inventory.persistence;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceFactoryImplTest {

	@Test
	@DisplayName("PersistenceFactory returns same instance when getting data store")
	void getInventoryDataStore() {
		PersistenceFactory factory = new PersistenceFactoryImpl();
		InventoryItems items = factory.getInventoryDataStore();
		InventoryItems items1 = factory.getInventoryDataStore();
		assertSame(items, items1);
	}
}