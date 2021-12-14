package com.wodder.inventory.domain;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

	private final InventoryItemStorage store = new TestDataStore();
	private ItemStorageService storage;

	@BeforeEach
	void setup() {
		storage = new ItemStorageService(store);
	}

	@Test
	@DisplayName("Can add new item")
	void adds_new_item() {
		InventoryItemModel itemData = InventoryItemModel.builder()
				.withName("2% Milk").build();

		Optional<InventoryItemModel> result = storage.addItem(itemData);

		assertTrue(result.isPresent());
		InventoryItemModel returned = result.get();
		assertEquals(1L, returned.getId());
	}

	@Test
	@DisplayName("Item without a location gets set to \"unassigned\"")
	void no_location_provided() {
		InventoryItemModel itemData = InventoryItemModel.builder()
				.withName("2% Milk").build();

		Optional<InventoryItemModel> result = storage.addItem(itemData);

		assertTrue(result.isPresent());
		InventoryItemModel returned = result.get();
		assertEquals(1L, returned.getId());
		assertEquals("unassigned", returned.getLocation());
	}

	@Test
	@DisplayName("New item shouldn't have id")
	void add_item_with_id() {
		InventoryItemModel itemDTO = InventoryItemModel.builder().withId(1L).build();

		Optional<InventoryItemModel> result = storage.addItem(itemDTO);
		assertFalse(result.isPresent());
	}

	@Test
	@DisplayName("New item requires name")
	void add_item_no_name() {
		InventoryItemModel itemDTO = InventoryItemModel.builder().build();
		Optional<InventoryItemModel> result = storage.addItem(itemDTO);
		assertFalse(result.isPresent());
	}

	@Test
	@DisplayName("Can delete an item")
	void delete_item() {
		InventoryItemModel itemDTO = InventoryItemModel.builder().withId(1L).withName("2% Milk").build();
		assertTrue(storage.deleteItem(itemDTO));
	}

	@Test
	@DisplayName("Deleting item requires an id")
	void delete_item_no_id() {
		InventoryItemModel itemDTO = InventoryItemModel.builder().withName("2% Milk").build();
		assertFalse(storage.deleteItem(itemDTO));
	}

	@Test
	@DisplayName("Can update an item")
	void update_item() {
		InventoryItemModel itemDTO = InventoryItemModel.builder().withId(1L).withName("2% Milk").build();

		Optional<InventoryItemModel> result = storage.updateItem(itemDTO);
		assertTrue(result.isPresent());
		InventoryItemModel updatedItem = result.get();
		assertEquals(itemDTO.getName(), updatedItem.getName());
		assertEquals(itemDTO.getId(), updatedItem.getId());
		assertNotSame(itemDTO, updatedItem);
	}

	@Test
	@DisplayName("Updating item requires id")
	void update_item_no_id() {
		InventoryItemModel itemDTO = InventoryItemModel.builder().withName("2% Milk").build();

		assertFalse(storage.updateItem(itemDTO).isPresent());
	}

	@Test
	@DisplayName("Can load existing item")
	void read_item() {
		Optional<InventoryItemModel> item = storage.readItem(1L);
		assertTrue(item.isPresent());
		InventoryItemModel result = item.get();
		assertEquals(1L, result.getId());
	}

	@Test
	@DisplayName("Id is required to load item")
	void read_item_bo_id() {
		Optional<InventoryItemModel> item = storage.readItem(null);
		assertFalse(item.isPresent());
	}

	@Test
	@DisplayName("Can load all available items")
	void read_all_items() {
		List<InventoryItemModel> result = storage.readAllItems();
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(3, result.size());
	}

	@Test
	@DisplayName("Newly added items are active by default")
	void active_by_default() {
		InventoryItemModel itemData = InventoryItemModel.builder()
				.withName("2% Milk").build();

		Optional<InventoryItemModel> result = storage.addItem(itemData);

		assertTrue(result.isPresent());
		InventoryItemModel returned = result.get();
		assertEquals(1L, returned.getId());
		assertTrue(returned.isActive());
	}

	private static class TestDataStore implements InventoryItemStorage {
		private Map<Long, InventoryItem> db = new HashMap<>();

		private TestDataStore() {
			db.put(1L, new InventoryItem(1L, "2% Milk", "Refridgerated"));
			db.put(2L, new InventoryItem(1L, "Almond Milk", "Refridgerated"));
			db.put(3L, new InventoryItem(1L, "Oatmeal", "Dry Goods"));
		}

		@Override
		public Optional<InventoryItem> loadItem(Long id) {
			Objects.requireNonNull(id);
			InventoryItem itemDTO = new InventoryItem(id, null, null);
			return Optional.of(itemDTO);
		}

		@Override
		public Optional<InventoryItem> updateItem(InventoryItem item) {
			Objects.requireNonNull(item);
			InventoryItem newItem = new InventoryItem(item.getId(), item.getName(), item.getCategory());
			return Optional.of(newItem);
		}

		@Override
		public Long createItem(InventoryItem item) {
			Objects.requireNonNull(item);
			return 1L;
		}

		@Override
		public boolean deleteItem(Long itemId) {
			Objects.requireNonNull(itemId);
			return true;
		}

		@Override
		public List<InventoryItem> loadAllItems() {
			return Collections.unmodifiableList(new ArrayList<>(db.values()));
		}

		@Override
		public List<InventoryItem> loadActiveItems() {
			return null;
		}

		@Override
		public List<InventoryCount> loadCounts() {
			return null;
		}
	}
}
