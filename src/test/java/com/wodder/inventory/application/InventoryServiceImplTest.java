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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

	@Mock
	Repository<Inventory, InventoryId> inventoryRepo;

	@InjectMocks
	InventoryServiceImpl svc;

	@Test
	@DisplayName("Can create a new inventory")
	void createInventory() {
		when(inventoryRepo.createItem(any())).thenReturn(new Inventory());
		InventoryModel mdl = svc.createInventory();
		assertNotNull(mdl);
		assertEquals(LocalDate.now(), mdl.getInventoryDate());
	}

	@Test
	@DisplayName("Creating a new inventory saves it to the database")
	void createInventory1() {
		when(inventoryRepo.createItem(any())).thenReturn(new Inventory());
		svc.createInventory();
		verify(inventoryRepo).createItem(any());
	}

	@Test
	@DisplayName("Can add new count to inventory")
	void addInventoryCount() {
		when(inventoryRepo.loadById(InventoryId.inventoryIdOf("123"))).thenReturn(Optional.of(new Inventory()));
		InventoryModel model = svc.addInventoryCount("123", "abc", 2.0, 0.25).get();
		assertEquals(1, model.numberOfItems());
		assertTrue(model.items().anyMatch(c -> c.getProductId().equals("abc")));
	}

	@Test
	@DisplayName("Invalid inventory id returns empty optional")
	void addInventoryCountUnknown() {
		when(inventoryRepo.loadById(InventoryId.inventoryIdOf("123"))).thenReturn(Optional.empty());
		assertTrue(svc.addInventoryCount("123", "abc", 2.0, 0.25).isEmpty());
	}
}
