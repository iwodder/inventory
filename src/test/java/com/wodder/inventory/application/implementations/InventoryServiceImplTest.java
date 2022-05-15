package com.wodder.inventory.application;

import com.wodder.inventory.application.implementations.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

	private static final ServiceFactory svc = new ServiceFactoryImpl(TestPersistenceFactory.getPopulated());
	InventoryService invSvc;

	@BeforeEach
	void setup() {
		invSvc = svc.getService(InventoryService.class);
	}

	@Test
	@DisplayName("Can create a new inventory")
	void createInventory() {
		InventoryModel mdl = invSvc.createInventory();
		assertNotNull(mdl);
		assertEquals(LocalDate.now(), mdl.getInventoryDate());
	}

	@Test
	@DisplayName("Creating a new inventory saves it to the database")
	void createInventory1() {
		invSvc.createInventory();
	}

	@Test
	@DisplayName("Can add new count to inventory")
	void addInventoryCount() {
		InventoryModel model = invSvc.addInventoryCount("123", "abc", 2.0, 0.25).get();
		assertEquals(1, model.numberOfItems());
//		assertTrue(model.items().anyMatch(c -> c.getProductId().equals("abc")));
	}

	@Test
	@DisplayName("Invalid inventory id returns empty optional")
	void addInventoryCountUnknown() {
		assertTrue(invSvc.addInventoryCount("123", "abc", 2.0, 0.25).isEmpty());
	}

	@Test
	@DisplayName("Can add multiple inventory counts to single inventory")
	void addInventoryCounts() {
		InventoryModel m = invSvc.addInventoryCounts("123", Arrays.asList(
				new InventoryCountModel("234", 1.0, 1.0),
				new InventoryCountModel("234", .23, 0.25),
				new InventoryCountModel("234", 1.1, 1.23))).get();
		assertEquals(3, m.numberOfItems());
	}
}
