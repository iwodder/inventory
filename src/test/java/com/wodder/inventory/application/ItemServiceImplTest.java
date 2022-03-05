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
class ItemServiceImplTest {

	@Mock
	Repository<Product, ProductId> productRepository;

	@Mock
	Repository<Category, CategoryId> categoryRepository;

	@Mock
	Repository<Location, LocationId> locationRepository;

	private ItemServiceImpl storage;

	@Captor
	ArgumentCaptor<Product> inventoryItemArgumentCaptor;

	@Captor
	ArgumentCaptor<Category> categoryArgumentCaptor;

	@Captor
	ArgumentCaptor<Location> locationArgumentCaptor;

	@BeforeEach
	void setup() {
		storage = new ItemServiceImpl(productRepository, categoryRepository, locationRepository);
	}

	@Test
	@DisplayName("Newly created item is active")
	void new_item_is_active() {
		when(locationRepository.loadByItem(new Location("Pantry")))
				.thenReturn(Optional.of(new Location("Pantry")));
		when(categoryRepository.loadByItem(new Category("Dry Goods")))
				.thenReturn(Optional.of(new Category("Dry Goods")));
		when(productRepository.createItem(any())).thenReturn(new Product(ProductId.productIdOf("1"),"2% Milk", new Category("Dairy"), new Location("Refrigerator")));
		ProductModel model = ProductModel.builder()
				.withName("Bread")
				.withLocation("Pantry")
				.withCategory("Dry Goods")
				.withUnitOfMeasurement("case")
				.withItemPrice("5.99")
				.withCasePrice("20.99")
				.build();
		storage.createNewItem(model);
		verify(productRepository).createItem(inventoryItemArgumentCaptor.capture());
		Product i = inventoryItemArgumentCaptor.getValue();
		assertEquals("Bread", i.getName());
		assertEquals("Pantry", i.getLocation());
		assertEquals("Dry Goods", i.getCategory());
		assertTrue(i.isActive());
	}

	@Test
	@DisplayName("Can delete an item")
	void delete_item() {
		when(productRepository.deleteItem(ProductId.productIdOf("1L"))).thenReturn(true);
		ProductModel itemDTO = ProductModel.builder().withId("1L").withName("2% Milk").build();
		assertTrue(storage.deleteItem(itemDTO));
	}

	@Test
	@DisplayName("Deleting item requires an id")
	void delete_item_no_id() {
		ProductModel itemDTO = ProductModel.builder().withName("2% Milk").build();
		assertFalse(storage.deleteItem(itemDTO));
	}

	@Test
	@DisplayName("Able to update item category")
	void update_item_category() {
		when(productRepository.loadById(ProductId.productIdOf("abc123"))).thenReturn(Optional.of(
				new Product(ProductId.productIdOf("abc123"), "2% Milk", new Category("Dairy"), new Location("Refrigerator"), true)));
		when(categoryRepository.loadByItem(new Category("Refrigerated"))).thenReturn(Optional.of(new Category("Refrigerated")));

		storage.updateItemCategory("abc123", "Refrigerated");

		verify(productRepository).updateItem(inventoryItemArgumentCaptor.capture());
		Product item = inventoryItemArgumentCaptor.getValue();
		assertEquals(ProductId.productIdOf("abc123"), item.getId());
		assertEquals("2% Milk", item.getName());
		assertEquals("Refrigerated", item.getCategory());
		assertEquals("Refrigerator", item.getLocation());
	}

	@Test
	@DisplayName("Able to update item name")
	void update_item_name() {
		when(productRepository.loadById(ProductId.productIdOf("abc123"))).thenReturn(
				Optional.of(new Product(ProductId.productIdOf("abc123"), "2% Milk", new Category("Dairy"), new Location("Refrigerator"), true)));

		storage.updateItemName("abc123", "2% Low-fat Milk");

		verify(productRepository).updateItem(inventoryItemArgumentCaptor.capture());

		Product item = inventoryItemArgumentCaptor.getValue();
		assertEquals(ProductId.productIdOf("abc123"), item.getId());
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
		when(productRepository.loadById(ProductId.productIdOf("abc123"))).thenReturn(Optional.of(new Product(ProductId.productIdOf("abc123"), "Bread", new Category("Dry Goods"), new Location("Pantry"))));
		Optional<ProductModel> item = storage.loadItem("abc123");
		assertTrue(item.isPresent());
		ProductModel result = item.get();
		assertEquals("abc123", result.getId());
	}

	@Test
	@DisplayName("Can update an item's location")
	void update_location() {
		when(productRepository.loadById(ProductId.productIdOf("1"))).thenReturn(Optional.of(new Product(ProductId.productIdOf("1"), "2% Milk", new Category("Refrigerated"), new Location("Refrigerator"))));
		when(locationRepository.loadByItem(new Location("Pantry"))).thenReturn(Optional.of(new Location("Pantry")));
		ProductModel model = storage.updateItemLocation("1", "Pantry").get();
		assertEquals("1", model.getId());
		assertEquals("Pantry", model.getLocation());
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
		when(productRepository.loadAllItems()).thenReturn(
				Arrays.asList(
						new Product( "Bread", new Category("Dry Goods"), new Location("Refrigerator")),
						new Product( "Yogurt", new Category("Dairy"), new Location("Refrigerator")),
						new Product( "Cereal", new Category("Dry Goods"), new Location("Pantry"))));
		List<ProductModel> result = storage.loadAllActiveItems();
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(3, result.size());
	}

	@Test
	@DisplayName("Can update the Unit of Measurement for an item")
	void update_uom() {
		when(productRepository.loadById(ProductId.productIdOf("1"))).thenReturn(Optional.of(new Product(ProductId.productIdOf("abc123"), "2% Milk", new Category("Refrigerated"), new Location("Refrigerator"), new UnitOfMeasurement("Quarts", 4))));
		Optional<ProductModel> opt = storage.updateItemUnitOfMeasurement("1", "Gallons", 4);
		assertTrue(opt.isPresent());
		ProductModel result = opt.get();
		assertEquals("Gallons", result.getUnits());
		assertEquals(4, result.getUnitsPerCase());
	}

	@Test
	@DisplayName("If item is not present then empty is returned")
	void item_not_found() {
		when(productRepository.loadById(ProductId.productIdOf("1"))).thenReturn(Optional.empty());
		Optional<ProductModel> opt = storage.updateItemUnitOfMeasurement("1", "Gallons", 4);
		assertFalse(opt.isPresent());
	}

	@Test
	@DisplayName("Can update the price for an item")
	void update_price() {
		when(productRepository.loadById(ProductId.productIdOf("1"))).thenReturn(Optional.of(new Product(
				ProductId.productIdOf("abc123"), "2% Milk", new Category("Refrigerated"), new Location("Refrigerator"),
				new UnitOfMeasurement("Quarts", 4), new Price("0.99", "1.99"))));
		Optional<ProductModel> opt = storage.updateItemPrice("1", "0.68", "19.23");
		assertTrue(opt.isPresent());
		ProductModel result = opt.get();
		assertEquals("0.68", result.getItemPrice());
		assertEquals("19.23", result.getCasePrice());
	}

	@Test
	@DisplayName("Creates a category when it doesn't exist")
	void creates_category() {
		when(categoryRepository.loadByItem(any())).thenReturn(Optional.empty());
		when(categoryRepository.createItem(any())).thenReturn(new Category("Frozen"));
		when(locationRepository.loadByItem(any())).thenReturn(Optional.empty());
		when(locationRepository.createItem(any())).thenReturn(new Location("Freezer"));
		when(productRepository.createItem(any())).thenReturn(new Product(ProductId.productIdOf("1"),"2% Milk", new Category("Dairy"), new Location("Refrigerator")));
		storage.createNewItem("2% Milk", "Frozen", "Refrigerator", "Gallons", 4, "2.98", "5.98");
		verify(categoryRepository).createItem(categoryArgumentCaptor.capture());
	}

	@Test
	@DisplayName("Creates a location when it doesn't exist")
	void creates_location() {
		when(categoryRepository.loadByItem(any())).thenReturn(Optional.empty());
		when(categoryRepository.createItem(any())).thenReturn(new Category("Frozen"));
		when(locationRepository.loadByItem(any())).thenReturn(Optional.empty());
		when(locationRepository.createItem(any())).thenReturn(new Location("Freezer"));
		when(productRepository.createItem(any())).thenReturn(new Product(ProductId.productIdOf("1"),"2% Milk", new Category("Dairy"), new Location("Refrigerator")));
		storage.createNewItem("2% Milk", "Frozen", "Freezer", "Quarts", 4, "2.98", "5.98");
		verify(locationRepository).createItem(locationArgumentCaptor.capture());
	}
}
