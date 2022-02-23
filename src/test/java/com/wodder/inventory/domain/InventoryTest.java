package com.wodder.inventory.domain;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
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
	@DisplayName("Inventory tracks total counted items")
	void total_items() {
		Inventory inventory = new Inventory();
		inventory.addInventoryCount(new InventoryCount(1L, "2% Milk", "Refrigerated", "Refrigerator", 1));
		assertEquals(1, inventory.numberOfItems());
	}

	@Test
	@DisplayName("Can add count to inventory")
	void add_item() {
		Inventory inventory = new Inventory();
		inventory.addInventoryCount(new InventoryCount(1L, "2% Milk", "Refrigerator", "Refrigerator", 5));
		assertEquals(1, inventory.numberOfItems());
	}

	@Test
	@DisplayName("Can add multiple counts with same category")
	void add_items() {
		Inventory inventory = new Inventory();
		inventory.addInventoryCount(new InventoryCount(1L, "2% Milk", "Refrigerator", "Refrigerator", 3));
		inventory.addInventoryCount(new InventoryCount(1L, "Cheese", "Refrigerator", "Refrigerator", 4));
		assertEquals(2, inventory.numberOfItems());
	}

	@Test
	@DisplayName("Can get counts by category")
	void get_item() {
		Inventory i = new Inventory();
		i.addInventoryCount(new InventoryCount(1L, "2% Milk", "Refrigerated", "Refrigerator", 0));
		i.addInventoryCount(new InventoryCount(2L, "Bread", "Dry", "Refrigerator", 3));
		assertEquals(1, i.getItemsByCategory("refrigerated").size());
		assertEquals(1, i.getItemsByCategory("dry").size());
		assertEquals(0, i.getItemsByCategory("frozen").size());
	}

	@Test
	@DisplayName("Copy constructor makes a deep copy of counts")
	void deep_copy() {
		Inventory i = new Inventory();
		i.addInventoryCount(new InventoryCount(1L, "2% Milk", "Refrigerated", "Refrigerator", 4));
		i.addInventoryCount(new InventoryCount(2L, "Bread", "Dry", "Refrigerator", 5));

		Inventory i2 = new Inventory(i);
		assertNotSame(i, i2);
		assertEquals(i.numberOfItems(), i2.numberOfItems());
		InventoryCount item = i.getCount("2% Milk").get();
		InventoryCount item2 = i2.getCount("2% Milk").get();
		assertNotSame(item, item2);
	}

	@Test
	@DisplayName("Can create an Inventory from InventoryModel")
	void from_model() {
		InventoryModel model = new InventoryModel();
		model.setInventoryDate(LocalDate.now());
		model.addInventoryCountModel(new InventoryCountModel(1L, "Pistachios", "Dry Goods", "Pantry", 4));
		model.addInventoryCountModel(new InventoryCountModel(1L, "2% Milk", "Refrigerated", "Refrigerator", 5));
		Inventory i = new Inventory(model);

		assertEquals(model.getInventoryDate(), i.date());
		assertEquals(model.numberOfItems(), i.numberOfItems());
	}

	@Test
	@DisplayName("Inventory is created in the OPEN state")
	void open_state() {
		Inventory i = new Inventory();
		assertTrue(i.isOpen());
	}

	@Test
	@DisplayName("Inventory can be closed")
	void closed_state() {
		Inventory i = new Inventory();
		i.finish();
		assertFalse(i.isOpen());
	}

	@Test
	@DisplayName("Adding an item to a closed inventory causes IllegalStateException")
	void add_when_closed() {
		Inventory i = new Inventory();
		i.finish();
		assertThrows(IllegalStateException.class, () -> i.addInventoryCount(new InventoryCount(1L, "2% Milk", "Refrigerated", "Refrigerator", 4)));
	}

}
