package com.wodder.inventory.application.implementations;

import com.wodder.inventory.application.*;
import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceImplTest {

	private ItemService storage;

	@BeforeEach
	void setup() {
		PersistenceFactory psf = new PersistenceFactoryImpl();
		new DataPopulation().initForTesting(psf);
		storage = new ItemServiceImpl(psf.getRepository(Product.class), psf.getRepository(Category.class), psf.getRepository(Location.class));
	}

	@Test
	@DisplayName("Newly created item is active")
	void new_item_is_active() {
		ProductModel model = getDefaultItem().build();
		Optional<ProductModel> i = storage.createNewItem(model);
		assertTrue(i.isPresent());
		ProductModel m = i.get();
		assertTrue(m.isActive());
	}

	@Test
	@DisplayName("Can delete an item")
	void delete_item() {
		ProductModel model = getDefaultItem().build();
		Optional<ProductModel> opt = storage.createNewItem(model);
		assertTrue(storage.deleteItem(opt.get().getId()));
	}

	@Test
	@DisplayName("Deleting item requires an id")
	void delete_item_no_id() {
		assertFalse(storage.deleteItem(null));
	}

	@Test
	@DisplayName("Able to update item category")
	void update_item_category() {
		ProductModel model = storage.createNewItem(getDefaultItem().build()).get();
		ProductModel result = storage.updateItemCategory(model.getId(), "Frozen").get();
		assertEquals("Frozen", result.getCategory());
	}

	@Test
	@DisplayName("Able to update item name")
	void update_item_name() {
		ProductModel model = storage.createNewItem(getDefaultItem().withName("Cheese").build()).get();
		ProductModel result =storage.updateItemName(model.getId(), "2% Low-fat Milk").get();
		assertEquals(result.getName(), "2% Low-fat Milk");
	}

	@Test
	@DisplayName("Updating item category requires id")
	void update_item_no_id() {
		assertFalse(storage.updateItemCategory(null, "2% Milk").isPresent());
	}

	@Test
	@DisplayName("Can load existing item")
	void read_item() {
		ProductModel newItem = storage.createNewItem(getDefaultItem().build()).get();
		Optional<ProductModel> result = storage.loadItem(newItem.getId());
		assertTrue(result.isPresent());
	}

	@Test
	@DisplayName("Can update an item's location")
	void update_location() {
		ProductModel newItem = storage.createNewItem(getDefaultItem().withLocation("Freezer").build()).get();
		ProductModel result = storage.updateItemLocation(newItem.getId(),"Pantry").get();
		assertEquals(result.getLocation(), "Pantry");
	}

	@Test
	@DisplayName("Id is required to load item")
	void read_item_bo_id() {
		Optional<ProductModel> item = storage.loadItem(null);
		assertFalse(item.isPresent());
	}

	@Test
	@DisplayName("Can load all available items")
	void read_all_items() {
		List<ProductModel> result = storage.loadAllActiveItems();
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(4, result.size());
	}

	@Test
	@DisplayName("Can update the Unit of Measurement for an item")
	void update_uom() {
		ProductModel newItem = storage.createNewItem(getDefaultItem().withLocation("Freezer").build()).get();
		ProductModel result = storage.updateItemUnitOfMeasurement(newItem.getId(), "Gallons", 4).get();
		assertEquals("Gallons", result.getUnits());
		assertEquals(4, result.getUnitsPerCase());
	}

	@Test
	@DisplayName("If item is not present then empty is returned")
	void item_not_found() {
		Optional<ProductModel> opt = storage.updateItemUnitOfMeasurement("1", "Gallons", 4);
		assertFalse(opt.isPresent());
	}

	@Test
	@DisplayName("Can update the price for an item")
	void update_price() {
		ProductModel newItem = storage.createNewItem(getDefaultItem().withLocation("Freezer").build()).get();
		ProductModel result = storage.updateItemPrice(newItem.getId(), "0.68", "19.23").get();
		assertEquals("0.68", result.getItemPrice());
		assertEquals("19.23", result.getCasePrice());
	}

	@Test
	@DisplayName("Creates a category when it doesn't exist")
	void creates_category() {
		storage.createNewItem("2% Milk", "Frozen", "Refrigerator", "Gallons", 4, "2.98", "5.98");
	}

	@Test
	@DisplayName("Creates a location when it doesn't exist")
	void creates_location() {
		storage.createNewItem("2% Milk", "Frozen", "Freezer", "Quarts", 4, "2.98", "5.98");
	}

	private ProductModel.ProductModelBuilder getDefaultItem() {
		return ProductModel.builder()
				.withName("Bread")
				.withLocation("Pantry")
				.withCategory("Dry Goods")
				.withUnitOfMeasurement("case")
				.withItemPrice("5.99")
				.withCasePrice("20.99");
	}
}
