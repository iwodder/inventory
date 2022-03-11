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
		inventory.addInventoryCount(new InventoryCount(InventoryId.inventoryIdOf("1"), ProductId.productIdOf("a"), 2, 4));
		assertEquals(1, inventory.numberOfItems());
	}

	@Test
	@DisplayName("Can add count to inventory")
	void add_item() {
		Inventory inventory = new Inventory();
		inventory.addInventoryCount(new InventoryCount(InventoryId.inventoryIdOf("1"), ProductId.productIdOf("a"), 2, 4));
		assertEquals(1, inventory.numberOfItems());
	}

	@Test
	@DisplayName("Can add multiple counts with same category")
	void add_items() {
		Inventory inventory = new Inventory();
		inventory.addInventoryCount(new InventoryCount(InventoryId.inventoryIdOf("1"), ProductId.productIdOf("a"), 2, 4));
		inventory.addInventoryCount(new InventoryCount(InventoryId.inventoryIdOf("1"), ProductId.productIdOf("a"), 2, 4));
		assertEquals(2, inventory.numberOfItems());
	}

	@Test
	@DisplayName("Copy constructor makes a deep copy of counts")
	void deep_copy() {
		Inventory i = new Inventory();
		i.addInventoryCount(new InventoryCount(InventoryId.inventoryIdOf("1"), ProductId.productIdOf("a"), 2, 4));
		i.addInventoryCount(new InventoryCount(InventoryId.inventoryIdOf("1"), ProductId.productIdOf("b"), 2, 4));

		Inventory i2 = new Inventory(i);
		assertNotSame(i, i2);
		assertEquals(i.numberOfItems(), i2.numberOfItems());
		InventoryCount item = i.getCount(ProductId.productIdOf("a")).get();
		InventoryCount item2 = i2.getCount(ProductId.productIdOf("a")).get();
		assertNotSame(item, item2);
	}

	@Test
	@DisplayName("Can create an Inventory from InventoryModel")
	void from_model() {
		InventoryModel model = new InventoryModel();
		model.setInventoryDate(LocalDate.now());
		model.addInventoryCountModel(new InventoryCountModel("1", "a", 1, 4));
		model.addInventoryCountModel(new InventoryCountModel("1", "b", 1, 5));
		Inventory i = new Inventory(model);

		assertEquals(model.getInventoryDate(), i.date());
		assertEquals(model.numberOfItems(), i.numberOfItems());
	}

	@Test
	@DisplayName("Copy constructor makes sure that InventoryState is set")
	void copy_state() {
		Inventory i = new Inventory();
		Inventory i2 = new Inventory(i);
		assertTrue(i2.isOpen());
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
		assertThrows(IllegalStateException.class, () -> i.addInventoryCount(new InventoryCount(InventoryId.inventoryIdOf("1"), ProductId.productIdOf("2"), 3, 4)));
	}

}
