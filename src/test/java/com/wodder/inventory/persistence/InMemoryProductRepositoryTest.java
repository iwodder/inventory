package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProductRepositoryTest {
	private InMemoryProductRepository inventoryItemStorage;

	@BeforeEach
	void setup() {
		inventoryItemStorage = new InMemoryProductRepository();
	}

	@Test
	@DisplayName("Can load a saved item")
	void loadItem() {
		Product item1 = new Product( "2% Milk", new Category("REFRIGERATED"), new Location("REFRIGERATOR"));
		ProductId id = inventoryItemStorage.createItem(item1).getId();

		Optional<Product> result = inventoryItemStorage.loadById(id);
		assertTrue(result.isPresent());
		Product item2 = result.get();
		assertEquals(item1.getName(), item2.getName());
	}

	@Test
	@DisplayName("Loading a non-existent item returns an empty optional")
	void load_does_not_exist() {
		Optional<Product> result = inventoryItemStorage.loadById(ProductId.productIdOf("2"));
		assertFalse(result.isPresent());
	}

	@Test
	@DisplayName("Able to update an item")
	void updateItem() {
		ProductId id = inventoryItemStorage.createItem(new Product("2% MILK", new Category("REFRIGERATED"), new Location("REFRIGERATOR"))).getId();

		Product item2 = new Product(id,"2% MILK", new Category("REFRIGERATED"), new Location("REFRIGERATOR"));
		Optional<Product> result = inventoryItemStorage.updateItem(item2);
		assertTrue(result.isPresent());
		Product updated = result.get();
		assertNotSame(item2, updated);
		assertEquals(item2.getName(), updated.getName());
		assertEquals("REFRIGERATED", updated.getCategory());
	}

	@Test
	@DisplayName("Item must be present to update")
	void update_item_not_present() {
		Product item = new Product(ProductId.productIdOf("1"), "2% Milk", new Category("REFRIGERATED"), new Location("REFRIGERATOR"));
		Optional<Product> result = inventoryItemStorage.updateItem(item);
		assertFalse(result.isPresent());
	}

	@Test
	@DisplayName("Can store an item")
	void createItem() {
		Product item = new Product( "2% Milk", new Category("REFRIGERATED"), new Location("REFRIGERATOR"));
		Product result = inventoryItemStorage.createItem(item);
		assertNotNull(result);
	}

	@Test
	@DisplayName("Can delete an item")
	void deleteItem() {
		Product item = new Product( "2% Milk", new Category("REFRIGERATED"), new Location("REFRIGERATOR"));
		ProductId id = inventoryItemStorage.createItem(item).getId();

		assertTrue(inventoryItemStorage.deleteItem(id));
		assertFalse(inventoryItemStorage.loadById(id).isPresent());
	}

	@Test
	@DisplayName("Null id returns false")
	void delete_item_null_id() {
		assertFalse(inventoryItemStorage.deleteItem((ProductId) null));
	}

	@Test
	@DisplayName("Can load all inventory items")
	void load_all_items() {
		inventoryItemStorage.createItem(new Product( "2% Milk", new Category("Refrigerated"), new Location("REFRIGERATOR")));
		List<Product> items = inventoryItemStorage.loadAllItems();
		assertNotNull(items);
		assertFalse(items.isEmpty());
		items.forEach(i -> assertNotNull(i.getId()));
	}

	@Test
	@DisplayName("Can load all active inventory items")
	void load_active() {
		Product i1 = new Product("Bananas", new Category("Fruit"), new Location("Pantry"));
		Product i2 = new Product("Kiwis", new Category("Fruit"), new Location("Counter"));
		Product i3 = new Product("Apples", new Category("Fruit"), new Location("Counter"));
		i3.inactivate();
		inventoryItemStorage.createItem(i1);
		inventoryItemStorage.createItem(i2);
		inventoryItemStorage.createItem(i3);
		List<Product> actives = inventoryItemStorage.loadActiveItems();
		assertEquals(2, actives.size());
	}

	@Test
	@DisplayName("Loading all active items on empty store returns empty list")
	void empty_store() {
		assertEquals(0, inventoryItemStorage.loadActiveItems().size());
	}
}
