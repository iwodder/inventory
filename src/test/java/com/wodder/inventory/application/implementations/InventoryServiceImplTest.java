package com.wodder.inventory.application.implementations;

import com.wodder.inventory.application.*;
import com.wodder.inventory.domain.model.inventory.*;
import com.wodder.inventory.domain.model.product.*;
import com.wodder.inventory.dto.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

	InventoryService invSvc;
	PersistenceFactory psf;

	@BeforeEach
	void setup() {
		psf = TestPersistenceFactory.getUnpopulated();
		invSvc = new InventoryServiceImpl(psf.getRepository(Inventory.class), psf.getRepository(Product.class));
	}

	@Test
	@DisplayName("Can create a new inventory")
	void createInventory() {
		InventoryDto mdl = invSvc.createInventory();
		assertNotNull(mdl);
		assertEquals(LocalDate.now(), mdl.getInventoryDate());
	}

	@Test
	@DisplayName("Creating a new inventory saves it to the database")
	void createInventory1() {
		InventoryDto model = invSvc.createInventory();
		InventoryDto result = invSvc.loadInventory(model.getId()).get();
		assertEquals(model, result);
	}

	@Test
	@DisplayName("Can add new count to inventory")
	void addInventoryCount() {
		InventoryDto dto = invSvc.createInventory();
		Repository<Product, ProductId> product = psf.getRepository(Product.class);
		product.createItem(new Product(ProductId.productIdOf("abc"),
				"2% Milk", new Category("Dairy"), new Location("Refrigerator"),
				new UnitOfMeasurement("GAL", 4), new Price("2.35", "11.00")));

		InventoryDto model = invSvc.addInventoryCount(dto.getId(), "abc", 2.0, 0.25).get();

		assertEquals(1, model.numberOfItems());
	}

	@Test
	@DisplayName("Invalid inventory id returns empty optional")
	void addInventoryCountUnknown() {
		assertTrue(invSvc.addInventoryCount("123", "abc", 2.0, 0.25).isEmpty());
	}

	@Test
	@DisplayName("Can add multiple inventory counts to single inventory")
	void addInventoryCounts() {
		InventoryDto inv = invSvc.createInventory();
		Repository<Product, ProductId> product = psf.getRepository(Product.class);
		product.createItem(new Product(ProductId.productIdOf("234"),
				"2% Milk", new Category("Dairy"), new Location("Refrigerator"),
				new UnitOfMeasurement("GAL", 4), new Price("2.35", "11.00")));
		product.createItem(new Product(ProductId.productIdOf("345"),
				"Cheese", new Category("Dairy"), new Location("Refrigerator"),
				new UnitOfMeasurement("GAL", 4), new Price("2.35", "11.00")));
		product.createItem(new Product(ProductId.productIdOf("456"),
				"Yogurt", new Category("Dairy"), new Location("Refrigerator"),
				new UnitOfMeasurement("GAL", 4), new Price("2.35", "11.00")));

		InventoryDto m = invSvc.addInventoryCounts(inv.getId(), Arrays.asList(
				new InventoryCountModel("234", 1.0, 1.0),
				new InventoryCountModel("345", .23, 0.25),
				new InventoryCountModel("456", 1.1, 1.23))).get();

		assertEquals(3, m.numberOfItems());
	}
}
