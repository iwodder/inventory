package com.wodder.inventory.application;

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
	private InventoryModel inventory;

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
		activeItems.add(new InventoryCount(1L, "2% Milk", "refrigerated", "Refrigerator"));
		InventoryModel result = service.createNewInventory();
		assertEquals(1, result.numberOfItems());
	}

	@Test
	@DisplayName("Saving inventory requires having items")
	void saves_inventory_false() {
		inventory = new InventoryModel();
		assertFalse(service.saveInventory(inventory));
	}

	@Test
	@DisplayName("Saving inventory requires having items")
	void saves_inventory_true() {
		setupTestInventory(LocalDate.now());
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
	@DisplayName("Loading inventory requires providing a date")
	void loads_completed_inventory() {
		when(storage.load(any())).thenReturn(Optional.of(new Inventory()));
		Optional<InventoryModel> inventory = service.loadInventory(LocalDate.now());
		assertTrue(inventory.isPresent());
	}

	@Test
	@DisplayName("Loading inventory requires a date")
	void loading_inventory_requires_a_date() {
		Optional<InventoryModel> inventory = service.loadInventory(null);
		assertTrue(inventory.isEmpty());
		verifyNoInteractions(storage);
	}

	@Test
	@DisplayName("Loading inventory requires today's date or earlier")
	void loading_inventory_requires_a_current_date() {
		Optional<InventoryModel> inventory = service.loadInventory(LocalDate.now().plusDays(1));
		assertTrue(inventory.isEmpty());
		verifyNoInteractions(storage);
	}

	private void stubActiveItemReturn() {
		when(items.loadCounts()).thenReturn(activeItems);
	}

	private void stubStorageSave(boolean result) {
		when(storage.save(any())).thenReturn(result);
	}

	private void setupTestInventory(LocalDate date) {
		inventory = new InventoryModel();
		if (date != null) {
			inventory.setInventoryDate(date);
		}
		inventory.addInventoryCountModel(
				new InventoryCountModel(1L, "Bread", "Dry", "Pantry"));
	}
}
