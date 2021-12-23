package com.wodder.inventory.domain;

import com.wodder.inventory.domain.entities.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

	@Test
	void inventory_item_is_equal_based_on_desc() {
		InventoryItem inv = new InventoryItem(1L, "2% Milk", new Category("Dry Goods"), new Location("pantry"));
		InventoryItem inv2 = new InventoryItem(2L,"2% Milk", new Category("Chemicals"), new Location("laundry"));
		assertEquals(inv, inv2);
	}

	@Test
	@DisplayName("Name is required for creating an item")
	void name_required() {
		assertThrows(IllegalArgumentException.class, () -> {
			InventoryItem i = new InventoryItem(null, new Category("Dry Goods"), new Location("Pantry"));
		});
	}

	@Test
	@DisplayName("Name cannot be blank")
	void blank_name() {
		assertThrows(IllegalArgumentException.class, () -> {
			InventoryItem i = new InventoryItem("", new Category("Dry Goods"), new Location("Pantry"));
		});
	}
}
