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

	@Mock
	Repository<Product, ProductId> productRepo;

	InventoryServiceImpl svc;

	@BeforeEach
	void setup() {
		svc = new InventoryServiceImpl(inventoryRepo, productRepo);
	}

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
//		assertTrue(model.items().anyMatch(c -> c.getProductId().equals("abc")));
	}

	@Test
	@DisplayName("Invalid inventory id returns empty optional")
	void addInventoryCountUnknown() {
		when(inventoryRepo.loadById(InventoryId.inventoryIdOf("123"))).thenReturn(Optional.empty());
		assertTrue(svc.addInventoryCount("123", "abc", 2.0, 0.25).isEmpty());
	}

	@Test
	@DisplayName("Can add multiple inventory counts to single inventory")
	void addInventoryCounts() {
		when(inventoryRepo.loadById(InventoryId.inventoryIdOf("123"))).thenReturn(Optional.of(new Inventory()));
		when(productRepo.loadById(any())).thenReturn(
				Optional.of(new Product("2% Milk", new Category("Dairy"), new Location("Refrigerator"))),
				Optional.of(new Product("Cheese", new Category("Dairy"), new Location("Refrigerator"))),
				Optional.of(new Product("Pistachios", new Category("Dry Goods"), new Location("Pantry")))
		);
		InventoryModel m = svc.addInventoryCounts("123", Arrays.asList(
				new InventoryCountModel("234", 1.0, 1.0),
				new InventoryCountModel("234", .23, 0.25),
				new InventoryCountModel("234", 1.1, 1.23))).get();
		assertEquals(3, m.numberOfItems());
	}
}
