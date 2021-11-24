package com.wodder.inventory.domain;

import org.junit.jupiter.api.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

	@Test
	@DisplayName("Inventory is created with current date")
	void current_date() {
		Inventory inventory = new Inventory();
		assertEquals(LocalDate.now(), inventory.date());
	}

	@Test
	@DisplayName("Inventory tracks total items")
	void total_items() {
		Inventory inventory = new Inventory();
		inventory.addInventoryItem(new InventoryItem(1L, "2% Milk", "Refridgerated"));
		assertEquals(1, inventory.numberOfItems());
	}

	@Test
	@DisplayName("Can add items to inventory")
	void add_item() {
		Inventory inventory = new Inventory();
		inventory.addInventoryItem(new InventoryItem(1L, "2% Milk", "Refrigerator"));
		assertEquals(1, inventory.numberOfItems());
	}

	@Test
	@DisplayName("Can add multiple items with same category")
	void add_items() {
		Inventory inventory = new Inventory();
		inventory.addInventoryItem(new InventoryItem(1L, "2% Milk", "Refrigerator"));
		inventory.addInventoryItem(new InventoryItem(1L, "Bread", "Refrigerator"));
		assertEquals(2, inventory.numberOfItems());
	}

	@Test
	@DisplayName("Can get items by category")
	void get_item() {
		Inventory i = new Inventory();
		i.addInventoryItem(new InventoryItem(1L, "2% Milk", "Refrigerated"));
		i.addInventoryItem(new InventoryItem(2L, "Bread", "Dry"));
		assertEquals(1, i.getItemsByCategory("refrigerated").size());
		assertEquals(1, i.getItemsByCategory("dry").size());
		assertEquals(0, i.getItemsByCategory("frozen").size());
	}
}
