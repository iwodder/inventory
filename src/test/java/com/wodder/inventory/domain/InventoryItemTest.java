package com.wodder.inventory.domain;

import com.wodder.inventory.domain.entities.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

	@Test
	@DisplayName("Identical values are equal")
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

	@Test
	@DisplayName("Category is required")
	void null_category() {
		assertThrows(IllegalArgumentException.class, () -> {
			InventoryItem i = new InventoryItem("Bread", null, new Location("Pantry"));
		});
	}

	@Test
	@DisplayName("Can successfully update a category")
	void update_category() {
		InventoryItem i = new InventoryItem("Bread", new Category("Dry Goods"), new Location("Pantry"));
		i.updateCategory(new Category("Refrigerated"));
		assertEquals("Refrigerated", i.getCategory());
	}

	@Test
	@DisplayName("Trying to update with the same category causes IllegalArgumentException")
	void update_same_category() {
		final InventoryItem i = new InventoryItem("Bread", new Category("Dry Goods"), new Location("Pantry"));
		assertThrows(IllegalArgumentException.class, () -> i.updateCategory(new Category("Dry Goods")));

	}
}
