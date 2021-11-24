package com.wodder.inventory.domain;

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

	@Mock
	InventoryItems items;

	@InjectMocks
	InventoryService service;

	@BeforeEach
	void setup() {
		activeItems = new ArrayList<>();
		when(items.loadActiveItems()).thenReturn(activeItems);
	}

	@Test
	@DisplayName("Creates a new inventory")
	void new_inventory() {
		Inventory dto = service.createNewInventory();
		assertNotNull(dto);
		assertEquals(LocalDate.now(), dto.getInventoryDate());
	}

	@Test
	@DisplayName("New inventory has active items")
	void active_items() {
		activeItems.add(new InventoryItem(1L, "2% Milk", "refrigerated"));
		Inventory result = service.createNewInventory();
		assertEquals(1, result.numberOfItems());
	}

	@Test
	@DisplayName("Inventory service loads only active items from inventory items")
	void active_items_only() {
		service.createNewInventory();
		verify(items).loadActiveItems();
	}

	@Test
	@DisplayName("Saves inventory to a store")
	void saves_inventory() {
		fail();
	}
}
