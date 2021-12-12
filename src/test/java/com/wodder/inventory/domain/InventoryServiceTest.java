package com.wodder.inventory.domain;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
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

	private List<InventoryCount> activeItems;
	private Inventory inventory;

	@Mock
	InventoryItemStorage items;

	@Mock
	InventoryStorage storage;

	@InjectMocks
	InventoryService service;

	@BeforeEach
	void setup() {
		activeItems = new ArrayList<>();
	}

	@Test
	@DisplayName("Creates a new inventory model")
	void new_inventory() {
		stubActiveItemReturn();
		InventoryModel dto = service.createNewInventory();
		assertNotNull(dto);
	}

	@Test
	@DisplayName("New inventory has active items")
	void active_items() {
		stubActiveItemReturn();
		activeItems.add(new InventoryCount(1L, "2% Milk", "refrigerated"));
		InventoryModel result = service.createNewInventory();
		assertEquals(1, result.numberOfItems());
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

	@Test
	@DisplayName("Loading inventory requires providing a date")
	void loads_completed_inventory() {
		when(storage.load(any())).thenReturn(Optional.of(new Inventory()));
		Optional<Inventory> inventory = service.loadInventory(LocalDate.now());
		assertTrue(inventory.isPresent());
	}

	@Test
	@DisplayName("Loading inventory requires a date")
	void loading_inventory_requires_a_date() {
		Optional<Inventory> inventory = service.loadInventory(null);
		assertTrue(inventory.isEmpty());
		verifyNoInteractions(storage);
	}

	@Test
	@DisplayName("Loading inventory requires today's date or earlier")
	void loading_inventory_requires_a_current_date() {
		Optional<Inventory> inventory = service.loadInventory(LocalDate.now().plusDays(1));
		assertTrue(inventory.isEmpty());
		verifyNoInteractions(storage);
	}


	private void stubActiveItemReturn() {
		when(items.loadCounts()).thenReturn(activeItems);
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
		inventory.addInventoryCount(new InventoryCount(1L, "Bread", "Dry", 0));
	}
}
