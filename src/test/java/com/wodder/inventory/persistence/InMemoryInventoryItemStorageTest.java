package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryInventoryItemStorageTest {
	private InventoryItemStorage inventoryItemStorage;

	@BeforeEach
	void setup() {
		inventoryItemStorage = new InMemoryInventoryItemStorage();
	}

	@Test
	@DisplayName("Can load a saved item")
	void loadItem() {
		InventoryItem item1 = new InventoryItem(null, "2% Milk", null);
		Long id = inventoryItemStorage.createItem(item1);

		Optional<InventoryItem> result = inventoryItemStorage.loadItem(id);
		assertTrue(result.isPresent());
		InventoryItem item2 = result.get();
		assertEquals(item1.getName(), item2.getName());
	}

	@Test
	@DisplayName("Loading a non-existent item returns an empty optional")
	void load_does_not_exist() {
		Optional<InventoryItem> result = inventoryItemStorage.loadItem(2L);
		assertFalse(result.isPresent());
	}

	@Test
	@DisplayName("Able to update an item")
	void updateItem() {
		Long id = inventoryItemStorage.createItem(new InventoryItem(null, "2% Milk", null));

		InventoryItem item2 = new InventoryItem(id,"2% MILK", "REFRIGERATED");
		Optional<InventoryItem> result = inventoryItemStorage.updateItem(item2);
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
		Optional<InventoryItem> result = inventoryItemStorage.updateItem(item);
		assertFalse(result.isPresent());
	}

	@Test
	@DisplayName("Can store an item")
	void createItem() {
		InventoryItem itemDTO = new InventoryItem(null, "2% Milk", null);

		Long result = inventoryItemStorage.createItem(itemDTO);
		assertNotNull(result);
	}

	@Test
	@DisplayName("Can delete an item")
	void deleteItem() {
		InventoryItem item = new InventoryItem(null, "2% Milk", null);
		Long id = inventoryItemStorage.createItem(item);

		assertTrue(inventoryItemStorage.deleteItem(id));
		assertFalse(inventoryItemStorage.loadItem(id).isPresent());
	}

	@Test
	@DisplayName("Null id returns false")
	void delete_item_null_id() {
		assertFalse(inventoryItemStorage.deleteItem(null));
	}

	@Test
	@DisplayName("Can load all inventory items")
	void load_all_items() {
		inventoryItemStorage.createItem(new InventoryItem(null, "2% Milk", "Refridgerated"));
		List<InventoryItem> items = inventoryItemStorage.loadAllItems();
		assertNotNull(items);
		assertFalse(items.isEmpty());
		items.forEach(i -> assertNotNull(i.getId()));
	}

	@Test
	@DisplayName("Can load all active inventory items")
	void load_active() {
		InventoryItem i1 = new InventoryItem(InventoryItemModel.builder().isActive(true).build());
		InventoryItem i2 = new InventoryItem(InventoryItemModel.builder().isActive(true).build());
		InventoryItem i3 = new InventoryItem(InventoryItemModel.builder().isActive(false).build());
		inventoryItemStorage.createItem(i1);
		inventoryItemStorage.createItem(i2);
		inventoryItemStorage.createItem(i3);
		List<InventoryItem> actives = inventoryItemStorage.loadActiveItems();
		assertEquals(2, actives.size());
	}

	@Test
	@DisplayName("Loading all active items on empty store returns empty list")
	void empty_store() {
		assertEquals(0, inventoryItemStorage.loadActiveItems().size());
	}

	@Test
	@DisplayName("Can load InventoryCounts from storage")
	void loads_counts() {
		InventoryItem i1 = new InventoryItem(InventoryItemModel.builder().withName("2% Milk").withCategory("Refrigerated").isActive(true).build());
		InventoryItem i2 = new InventoryItem(InventoryItemModel.builder().withName("Cheese").withCategory("Refrigerated").isActive(true).build());
		InventoryItem i3 = new InventoryItem(InventoryItemModel.builder().withName("Pistachios").withCategory("Dry Goods").isActive(true).build());
		Long id = inventoryItemStorage.createItem(i1);
		inventoryItemStorage.createItem(i2);
		inventoryItemStorage.createItem(i3);
		List<InventoryCount> counts = inventoryItemStorage.loadCounts();
		assertEquals(3, counts.size());
	}

	@Test
	@DisplayName("Can save new on hand count")
	void save_count() {
		InventoryItem i1 = new InventoryItem(InventoryItemModel.builder().withName("2% Milk").withCategory("Refrigerated").isActive(true).build());
		Long id = inventoryItemStorage.createItem(i1);
		InventoryCount count = new InventoryCount(id, null, null, 2);
		inventoryItemStorage.updateCount(count);
		InventoryCount c2 = inventoryItemStorage.loadCount(id).get();
		assertEquals(2, c2.getCount());
	}

	@Test
	@DisplayName("Can load a single count")
	void load_count() {
		InventoryItem i1 = new InventoryItem(InventoryItemModel.builder().withName("2% Milk").withCategory("Refrigerated").isActive(true).build());
		Long id = inventoryItemStorage.createItem(i1);
		InventoryCount count = inventoryItemStorage.loadCount(id).get();
		assertEquals(id, count.getItemId());
		assertEquals(i1.getName(), count.getName());
		assertEquals(i1.getCategory(), count.getCategory());
		assertEquals(0, count.getCount());
	}
}
