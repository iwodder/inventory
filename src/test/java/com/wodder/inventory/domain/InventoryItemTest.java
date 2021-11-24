package com.wodder.inventory.domain;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

	@Test
	void inventory_item_is_equal_based_on_desc() {
		InventoryItem inv = new InventoryItem(1L, "2% Milk", "Dry Goods");
		InventoryItem inv2 = new InventoryItem(2L,"2% Milk", "Chemicals");
		assertEquals(inv, inv2);
	}

	@Test
	@DisplayName("Setting negative count causes illegal argument exception")
	void negative_count() {
		InventoryItem inv = new InventoryItem(null,null,null);
		assertThrows(IllegalArgumentException.class, () -> inv.setCount(-1));
	}
}
