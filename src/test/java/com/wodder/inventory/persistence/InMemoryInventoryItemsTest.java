package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryInventoryItemsTest {
	private InventoryItems inventoryItems;

	@BeforeEach
	void setup() {
		inventoryItems = new InMemoryInventoryItems();
	}

	@Test
	@DisplayName("Can load a saved item")
	void loadItem() {
		InventoryItem item1 = new InventoryItem(null, "2% Milk", null);
		Long id = inventoryItems.createItem(item1);

		Optional<InventoryItem> result = inventoryItems.loadItem(id);
		assertTrue(result.isPresent());
		InventoryItem item2 = result.get();
		assertEquals(item1.getName(), item2.getName());
	}

	@Test
	@DisplayName("Loading a non-existent item returns an empty optional")
	void load_does_not_exist() {
		Optional<InventoryItem> result = inventoryItems.loadItem(2L);
		assertFalse(result.isPresent());
	}

	@Test
	@DisplayName("Able to update an item")
	void updateItem() {
		InventoryItem item1 = new InventoryItem(null, "2% Milk", null);
		Long id = inventoryItems.createItem(item1);

		InventoryItem item2 = new InventoryItem(id,"2% MILK", null);
		Optional<InventoryItem> result = inventoryItems.updateItem(item2);
		InventoryItem updated = result.orElse(null);
		assertNotSame(item2, updated);
		assertEquals(item2.getName(), updated.getName());
	}

	@Test
	@DisplayName("Item must be present to update")
	void update_item_not_present() {
		InventoryItem item = new InventoryItem(1L, "2% Milk", null);
		Optional<InventoryItem> result = inventoryItems.updateItem(item);
		assertFalse(result.isPresent());
	}

	@Test
	@DisplayName("Can store an item")
	void createItem() {
		InventoryItem itemDTO = new InventoryItem(null, "2% Milk", null);

		Long result = inventoryItems.createItem(itemDTO);
		assertNotNull(result);
	}

	@Test
	@DisplayName("Can delete an item")
	void deleteItem() {
		InventoryItem item = new InventoryItem(null, "2% Milk", null);
		Long id = inventoryItems.createItem(item);

		assertTrue(inventoryItems.deleteItem(id));
		assertFalse(inventoryItems.loadItem(id).isPresent());
	}

	@Test
	@DisplayName("Null id returns false")
	void delete_item_null_id() {
		assertFalse(inventoryItems.deleteItem(null));
	}

	@Test
	@DisplayName("Can load all inventory items")
	void load_all_items() {
		inventoryItems.createItem(new InventoryItem(null, "2% Milk", "Refridgerated"));
		List<InventoryItem> items = inventoryItems.loadAllItems();
		assertNotNull(items);
		assertFalse(items.isEmpty());
	}
}
