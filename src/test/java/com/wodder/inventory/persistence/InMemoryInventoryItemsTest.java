package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.*;
import com.wodder.inventory.dtos.*;
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
		Long id = inventoryItems.createItem(new InventoryItem(null, "2% Milk", null));

		InventoryItem item2 = new InventoryItem(id,"2% MILK", "REFRIGERATED");
		Optional<InventoryItem> result = inventoryItems.updateItem(item2);
		assertTrue(result.isPresent());
		InventoryItem updated = result.get();
		assertNotSame(item2, updated);
		assertEquals(item2.getName(), updated.getName());
		assertEquals("REFRIGERATED", updated.getCategory());
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
		items.forEach(i -> assertNotNull(i.getId()));
	}

	@Test
	@DisplayName("Can load all active inventory items")
	void load_active() {
		InventoryItem i1 = new InventoryItem(InventoryItemDto.builder().isActive(true).build());
		InventoryItem i2 = new InventoryItem(InventoryItemDto.builder().isActive(true).build());
		InventoryItem i3 = new InventoryItem(InventoryItemDto.builder().isActive(false).build());
		inventoryItems.createItem(i1);
		inventoryItems.createItem(i2);
		inventoryItems.createItem(i3);
		List<InventoryItem> actives = inventoryItems.loadActiveItems();
		assertEquals(2, actives.size());
	}

	@Test
	@DisplayName("Loading all active items on empty store returns empty list")
	void empty_store() {
		assertEquals(0, inventoryItems.loadActiveItems().size());
	}
}
