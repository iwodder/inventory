package com.wodder.inventory.application;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageTest {

	@Mock
	private InventoryItemStorage store;

	@InjectMocks
	private ItemStorageService storage;

	@Test
	@DisplayName("Can add new item")
	void adds_new_item() {
		InventoryItemModel itemData = InventoryItemModel.builder()
				.withName("2% Milk").build();
		when(store.createItem(any())).thenReturn(1L);

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
		when(store.createItem(any())).thenReturn(1L);

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
		when(store.deleteItem(1L)).thenReturn(true);
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
		when(store.updateItem(any())).thenReturn(Optional.of(new InventoryItem(itemDTO)));
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
		when(store.loadItem(1L)).thenReturn(Optional.of(new InventoryItem(1L, "Bread", new Category("Dry Goods"), new Location("Pantry"))));
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
		when(store.loadAllItems()).thenReturn(
				Arrays.asList(
						new InventoryItem(1L, "Bread", new Category("Dry Goods"), new Location("Refrigerator")),
						new InventoryItem(2L, "Yogurt", new Category("Dairy"), new Location("Refrigerator")),
						new InventoryItem(3L, "Cereal", new Category("Dry Goods"), new Location("Pantry"))));
		List<InventoryItemModel> result = storage.readAllItems();
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(3, result.size());
	}

	@Test
	@DisplayName("Newly added items are active by default")
	void active_by_default() {
		when(store.createItem(any())).thenReturn(1L);
		InventoryItemModel itemData = InventoryItemModel.builder()
				.withName("2% Milk").build();

		Optional<InventoryItemModel> result = storage.addItem(itemData);

		assertTrue(result.isPresent());
		InventoryItemModel returned = result.get();
		assertEquals(1L, returned.getId());
		assertTrue(returned.isActive());
	}
}
