package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;
import org.junit.jupiter.api.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

	@Test
	@DisplayName("Inventory is created with current date")
	void current_date() {
		Inventory inventory = new Inventory();
		assertEquals(LocalDate.now(), inventory.date());
	}

	@Test
	@DisplayName("Can add count to inventory")
	@Disabled
	void add_item() {
		Inventory inventory = new Inventory();
		InventoryItem item = new InventoryItem(
				"2% Milk", "Refrigerator", "Dairy",
				new UnitOfMeasurement("Gallon", 4), new Price("0.99", "4.98"),
				new InventoryCount(1.0, 0.25)
				);
		inventory.addItemToInventory(item);
		assertEquals(1, inventory.numberOfItems());
	}

	@Test
	@DisplayName("Copy constructor makes a deep copy of counts")
	void deep_copy() {
		Inventory i = Inventory.createNewInventoryWithProducts(
				Arrays.asList(
						new Product("2% Milk", new Category("Dairy"), new Location("Refrigerator"), new UnitOfMeasurement("Gallon", 4), new Price("0.99", "4.98")),
						new Product("Chicken Breast", new Category("Meat"), new Location("Refrigerator"), new UnitOfMeasurement("Gallon", 4), new Price("0.99", "4.98"))
				)
		);

		Inventory i2 = new Inventory(i);
		assertNotSame(i, i2);
		assertEquals(i.numberOfItems(), i2.numberOfItems());
		InventoryCount count1 = i.getCount("2% Milk").get();
		InventoryCount count2 = i2.getCount("2% Milk").get();
		assertNotSame(count1, count2);
	}

	@Test
	@DisplayName("Can create an Inventory from InventoryModel")
	void from_model() {
		InventoryModel model = new InventoryModel();
		model.setInventoryDate(LocalDate.now());
		model.addInventoryItem(new ItemModel(new InventoryItem("2% Milk", "Refrigerator",
				"Dairy", new UnitOfMeasurement("Gallon", 4),
				new Price("0.99", "4.98"), new InventoryCount(1.0, 0.25))));
		model.addInventoryItem(new ItemModel(new InventoryItem("Shredded Cheese", "Refrigerator",
				"Dairy", new UnitOfMeasurement("Gallon", 4),
				new Price("0.99", "4.98"), new InventoryCount(1.0, 0.25))));
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
		assertThrows(IllegalStateException.class, () ->
				i.updateInventoryCount(
						"2% Milk", "Refrigerator", new InventoryCount(3, 4)));
	}

	@Test
	@DisplayName("Can add product to inventory")
	void add_product() {
		Inventory i = Inventory.createNewInventoryWithProducts(Arrays.asList(new Product("Name", new Category("Frozen"), new Location("Freezer"))));
		assertEquals(1, i.numberOfItems());
	}

	@Test
	@DisplayName("Can query inventory items by location")
	void query_by_location() {
		Inventory i = Inventory.createNewInventoryWithProducts(Arrays.asList(new Product("Name", new Category("Frozen"), new Location("Freezer"))));
		assertEquals(1, i.getItemsByLocation("Freezer").size());
		assertEquals(0, i.getItemsByLocation("Pantry").size());

	}

	@Test
	@DisplayName("Can query inventory items by category")
	void query_by_category() {
		Inventory i = Inventory.createNewInventoryWithProducts(Arrays.asList(new Product("Name", new Category("Frozen"), new Location("Freezer"))));
		assertEquals(1, i.getItemsByCategory("Frozen").size());
		assertEquals(0, i.getItemsByCategory("Dairy").size());
	}
}
