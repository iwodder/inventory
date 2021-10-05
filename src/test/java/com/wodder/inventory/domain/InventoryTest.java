package com.wodder.inventory.domain;

import org.junit.jupiter.api.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

	@Test
	void inventory_is_created_with_current_date() {
		Inventory inventory = new Inventory();
		assertEquals(LocalDate.now(), inventory.date());
	}

	@Test
	void inventory_track_total_items() {
		Inventory inventory = new Inventory();
		inventory.addInventoryItem(new InventoryItem(1L, "2% Milk", "Refridgerated"));
		assertEquals(1, inventory.numberOfItems());
	}

	@Test
	void can_add_item_to_inventory() {
		Inventory inventory = new Inventory();
		assertTrue(inventory.addInventoryItem(new InventoryItem(1L, "2% Milk", "Refridgerator")));
		assertFalse(inventory.addInventoryItem(null));
	}
}
