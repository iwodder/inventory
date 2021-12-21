package com.wodder.inventory.domain.entities;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemFactoryTest {

	@Test
	@DisplayName("Creates inventory items for system use")
	void creates_item() {
		InventoryItemFactory factory = new InventoryItemFactory();
		InventoryItem item = factory.createNewItem("Bread", "Dry Goods", "Pantry");
		assertNull(item.getId());
		assertEquals("Bread", item.getName());
		assertEquals("Dry Goods", item.getCategory());
		assertEquals("Pantry", item.getLocation());
	}

	@Test
	@DisplayName("Creates existing inventory items for system use")
	void create_existing() {
		InventoryItemFactory factory = new InventoryItemFactory();
		InventoryItem item = factory.createExistingItem(1L, "Bread", "Dry Goods", "Pantry");
		assertEquals(1L, item.getId());
		assertEquals("Bread", item.getName());
		assertEquals("Dry Goods", item.getCategory());
		assertEquals("Pantry", item.getLocation());
	}
}
