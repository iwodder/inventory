package com.wodder.inventory.domain;

import com.wodder.inventory.dtos.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

	private List<InventoryItem> activeItems;
	private Inventory inventory;

	@Mock
	InventoryItems items;

	@Mock
	InventoryStorage storage;

	@InjectMocks
	InventoryService service;

	@BeforeEach
	void setup() {
		activeItems = new ArrayList<>();
	}

	@Test
	@DisplayName("Creates a new inventory")
	void new_inventory() {
		stubActiveItemReturn();
		Inventory dto = service.createNewInventory();
		assertNotNull(dto);
		assertEquals(LocalDate.now(), dto.getInventoryDate());
	}

	@Test
	@DisplayName("New inventory has active items")
	void active_items() {
		stubActiveItemReturn();
		activeItems.add(new InventoryItem(1L, "2% Milk", "refrigerated"));
		Inventory result = service.createNewInventory();
		assertEquals(1, result.numberOfItems());
	}

	@Test
	@DisplayName("Inventory service loads only active items from inventory items")
	void active_items_only() {
		stubActiveItemReturn();
		service.createNewInventory();
		verify(items).loadActiveItems();
	}

	@Test
	@DisplayName("Saving inventory requires having items")
	void saves_inventory_false() {
		inventory = new Inventory();
		assertFalse(service.saveInventory(inventory));
	}

	@Test
	@DisplayName("Saving inventory requires having items")
	void saves_inventory_true() {
		setupTestInventory(null);
		stubStorageSave(true);
		assertTrue(service.saveInventory(inventory));
	}

	@Test
	@DisplayName("Saving inventory must not have past date")
	void date_in_past() {
		setupTestInventory(LocalDate.of(2021, 1, 1));
		assertFalse(service.saveInventory(inventory));
	}

	@Test
	@DisplayName("Saving inventory interacts with inventory storage")
	void stores_completed_inventory() {
		setupTestInventory(null);
		service.saveInventory(inventory);
		verify(storage).save(inventory);
	}


	private void stubActiveItemReturn() {
		when(items.loadActiveItems()).thenReturn(activeItems);
	}

	private void stubStorageSave(boolean result) {
		when(storage.save(inventory)).thenReturn(result);
	}

	private void setupTestInventory(LocalDate date) {
		if (date == null) {
			inventory = new Inventory();
		} else {
			inventory = new Inventory(date);
		}
		inventory.addInventoryItem(new InventoryItem(InventoryItemDto.builder()
				.withId(1L)
				.withName("Bread")
				.withCategory("Dry")
				.build()));
	}
}
