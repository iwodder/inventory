package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceFactoryImplTest {

	@Test
	@DisplayName("PersistenceFactory returns same instance when getting data store")
	void getInventoryDataStore() {
		PersistenceFactory factory = new PersistenceFactoryImpl();
		InventoryItemRepository items = factory.getInventoryDataStore();
		InventoryItemRepository items1 = factory.getInventoryDataStore();
		assertSame(items, items1);
	}

	@Test
	@DisplayName("Providing the class returns the correct data store")
	void data_store() {
		PersistenceFactory factory = new PersistenceFactoryImpl();
		Repository<Category> c = factory.getRepository(Category.class);
		assertNotNull(c);
		assertTrue(c instanceof InMemoryCategoryRepository);
	}
}
