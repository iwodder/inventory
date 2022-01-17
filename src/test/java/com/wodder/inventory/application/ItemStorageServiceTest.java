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
class ItemStorageServiceTest {

	@Mock
	private InventoryItemRepository store;

	@InjectMocks
	private ItemStorageService storage;

	@Captor
	ArgumentCaptor<InventoryItem> argumentCaptor;

	@Test
	@DisplayName("Can add new item")
	void adds_new_item() {
		InventoryItemModel itemData = InventoryItemModel.builder()
				.withName("2% Milk").withCategory("Dairy")
				.withLocation("Refrigerator").withUnitOfMeasurement("Gallon").withItemsPerCase(4)
				.withItemPrice("1.99").withCasePrice("4.98").build();

		when(store.saveItem(any())).thenReturn(1L);
		when(store.loadItem(1L)).thenReturn(Optional.of(new InventoryItem(1L,"2% Milk", new Category(), new Location())));

		Optional<InventoryItemModel> result = storage.addItem(itemData);

		verify(store).saveItem(argumentCaptor.capture());

	}

	@Test
	@DisplayName("Newly created item is active")
	void new_item_is_active() {
		when(store.loadLocation("Pantry")).thenReturn(Optional.of(new Location("Pantry")));
		when(store.loadCategory("Dry Goods")).thenReturn(Optional.of(new Category("Dry Goods")));
		InventoryItemModel model = InventoryItemModel.builder()
				.withName("Bread")
				.withLocation("Pantry")
				.withCategory("Dry Goods")
				.build();
		storage.addItem(model);
		verify(store).saveItem(argumentCaptor.capture());
		InventoryItem i = argumentCaptor.getValue();
		assertEquals("Bread", i.getName());
		assertEquals("Pantry", i.getLocation());
		assertEquals("Dry Goods", i.getCategory());
		assertTrue(i.isActive());
	}

	@Test
	@DisplayName("Item without a location gets set to \"unassigned\"")
	void no_location_provided() {
		InventoryItemModel itemData = InventoryItemModel.builder()
				.withName("2% Milk").build();
		when(store.saveItem(any())).thenReturn(1L);
		when(store.loadItem(1L)).thenReturn(
				Optional.of(new InventoryItem(1L, "2% Milk", new Category(), new Location())));

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
		assertThrows(IllegalArgumentException.class, () -> storage.addItem(itemDTO));
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
	@DisplayName("Able to update item category")
	void update_item_category() {
		when(store.loadItem(1L)).thenReturn(Optional.of(
				new InventoryItem(1L, "2% Milk", new Category("Dairy"), new Location("Refrigerator"), true)));
		when(store.loadCategory("Refrigerated")).thenReturn(Optional.of(new Category("Refrigerated")));

		storage.updateItemCategory(1L, "Refrigerated");

		verify(store).updateItem(argumentCaptor.capture());
		InventoryItem item = argumentCaptor.getValue();
		assertEquals(1L, item.getId());
		assertEquals("2% Milk", item.getName());
		assertEquals("Refrigerated", item.getCategory());
		assertEquals("Refrigerator", item.getLocation());
	}

	@Test
	@DisplayName("Able to update item name")
	void update_item_name() {
		when(store.loadItem(1L)).thenReturn(
				Optional.of(new InventoryItem(1L, "2% Milk", new Category("Dairy"), new Location("Refrigerator"), true)));

		storage.updateItemName(1L, "2% Low-fat Milk");

		verify(store).updateItem(argumentCaptor.capture());

		InventoryItem item = argumentCaptor.getValue();
		assertEquals(1L, item.getId());
		assertEquals("2% Low-fat Milk", item.getName());
		assertEquals("Dairy", item.getCategory());
		assertEquals("Refrigerator", item.getLocation());
	}

	@Test
	@DisplayName("Updating item category requires id")
	void update_item_no_id() {
		assertFalse(storage.updateItemCategory(null, "2% Milk").isPresent());
	}

	@Test
	@DisplayName("Can load existing item")
	void read_item() {
		when(store.loadItem(1L)).thenReturn(Optional.of(new InventoryItem(1L, "Bread", new Category("Dry Goods"), new Location("Pantry"))));
		Optional<InventoryItemModel> item = storage.loadItem(1L);
		assertTrue(item.isPresent());
		InventoryItemModel result = item.get();
		assertEquals(1L, result.getId());
	}

	@Test
	@DisplayName("Can update an item's location")
	void update_location() {
		when(store.loadItem(1L)).thenReturn(Optional.of(new InventoryItem(1L, "2% Milk", new Category("Refrigerated"), new Location("Refrigerator"))));
		when(store.loadLocation("Pantry")).thenReturn(Optional.of(new Location("Pantry")));
		InventoryItemModel model = storage.updateItemLocation(1L, "Pantry").get();
		assertEquals(1L, model.getId());
		assertEquals("Pantry", model.getLocation());
	}

	@Test
	@DisplayName("Id is required to load item")
	void read_item_bo_id() {
		Optional<InventoryItemModel> item = storage.loadItem(null);
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
		List<InventoryItemModel> result = storage.loadAllActiveItems();
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(3, result.size());
	}

	@Test
	@DisplayName("Can update the Unit of Measurement for an item")
	void update_uom() {
		when(store.loadItem(1L)).thenReturn(Optional.of(new InventoryItem(1L, "2% Milk", new Category("Refrigerated"), new Location("Refrigerator"), new UnitOfMeasurement("Quarts", 4))));
		Optional<InventoryItemModel> opt = storage.updateItemUnitOfMeasurement(1L, "Gallons", 4);
		assertTrue(opt.isPresent());
		InventoryItemModel result = opt.get();
		assertEquals("Gallons", result.getUnits());
		assertEquals(4, result.getUnitsPerCase());
	}

	@Test
	@DisplayName("If item is not present then empty is returned")
	void item_not_found() {
		when(store.loadItem(1L)).thenReturn(Optional.empty());
		Optional<InventoryItemModel> opt = storage.updateItemUnitOfMeasurement(1L, "Gallons", 4);
		assertFalse(opt.isPresent());
	}

	@Test
	@DisplayName("Can update the price for an item")
	void update_price() {
		when(store.loadItem(1L)).thenReturn(Optional.of(new InventoryItem(
				1L, "2% Milk", new Category("Refrigerated"), new Location("Refrigerator"),
				new UnitOfMeasurement("Quarts", 4), new Price("0.99", "1.99"))));
		Optional<InventoryItemModel> opt = storage.updateItemPrice(1L, "0.68", "19.23");
		assertTrue(opt.isPresent());
		InventoryItemModel result = opt.get();
		assertEquals("0.68", result.getItemPrice());
		assertEquals("19.23", result.getCasePrice());
	}
}
