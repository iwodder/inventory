package com.wodder.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.product.application.ReceiveShipmentCommand.Item;
import com.wodder.product.domain.model.category.Category;
import com.wodder.product.dto.ProductDto;
import com.wodder.product.persistence.PersistenceFactory;
import com.wodder.product.persistence.TestPersistenceFactory;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductServiceImplTest {

  private ProductService storage;

  @BeforeEach
  void setup() {
    PersistenceFactory psf = TestPersistenceFactory.getPopulated();
    storage =
        new ProductServiceImpl(
            psf.getProductRepository(),
            psf.getRepository(Category.class),
            psf.getShipmentRepository());
  }

  @Test
  @DisplayName("Should be able to create a new product")
  void create_new_product() {
    ProductDto model = getDefaultItem().build();
    Optional<ProductDto> i = storage.createNewProduct(model);
    assertTrue(i.isPresent());
  }

  @Test
  @DisplayName("Can delete an item")
  void delete_item() {
    ProductDto model = getDefaultItem().build();
    Optional<ProductDto> opt = storage.createNewProduct(model);
    assertTrue(storage.deleteProduct(opt.get().getId()));
  }

  @Test
  @DisplayName("Deleting item requires an id")
  void delete_item_no_id() {
    assertFalse(storage.deleteProduct(null));
  }

  @Test
  @DisplayName("Able to update item category")
  void update_item_category() {
    ProductDto model = storage.createNewProduct(getDefaultItem().build()).get();
    ProductDto result = storage.updateProductCategory(model.getId(), "Frozen").get();
    assertEquals("FROZEN", result.getCategory());
  }

  @Test
  @DisplayName("Able to update item name")
  void update_item_name() {
    ProductDto model =
        storage.createNewProduct(getDefaultItem().withName("Cheese").build()).get();
    ProductDto result = storage.updateProductName(model.getId(), "2% Low-fat Milk").get();
    assertEquals(result.getName(), "2% Low-fat Milk");
  }

  @Test
  @DisplayName("Updating item category requires id")
  void update_item_no_id() {
    assertFalse(storage.updateProductCategory(null, "2% Milk").isPresent());
  }

  @Test
  @DisplayName("Can load existing item")
  void read_item() {
    ProductDto newItem = storage.createNewProduct(getDefaultItem().build()).get();
    Optional<ProductDto> result = storage.loadProductById(newItem.getId());
    assertTrue(result.isPresent());
  }

  @Test
  @DisplayName("Id is required to load item")
  void read_item_bo_id() {
    Optional<ProductDto> item = storage.loadProductById(null);
    assertFalse(item.isPresent());
  }

  @Test
  @DisplayName("Can load all available items")
  void read_all_items() {
    List<ProductDto> result = storage.loadAllActiveProducts();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(4, result.size());
  }

  @Test
  @DisplayName("Can update the Unit of Measurement for an item")
  void update_uom() {
    ProductDto newItem =
        storage.createNewProduct(getDefaultItem().build()).get();
    ProductDto result =
        storage.updateProductUnitOfMeasurement(newItem.getId(), "Gallons").get();
    assertEquals("Gallons", result.getUnits());
  }

  @Test
  @DisplayName("If item is not present then empty is returned")
  void item_not_found() {
    Optional<ProductDto> opt = storage.updateProductUnitOfMeasurement("1", "Gallons");
    assertTrue(opt.isEmpty());
  }

  @Test
  @DisplayName("Can update the price for an item")
  void update_price() {
    ProductDto newItem = storage.createNewProduct(getDefaultItem().build()).get();

    ProductDto result = storage.updateProductPrice(newItem.getId(), "0.68", "19.23").get();

    assertEquals("0.68", result.getItemPrice());
    assertEquals("19.23", result.getCasePrice());
  }

  @Test
  @DisplayName("Can create a new product")
  void create_product() {
    CreateProductCommand cmd = new CreateProductCommand();
    cmd.setName("Cheddar Cheese");
    cmd.setCategoryId("c1");
    cmd.setUnitPrice("0.65");
    cmd.setUnitMeasurement("Ounces");
    cmd.setCasePack("4");
    cmd.setExternalId("sku123");

    String id = storage.createProduct(cmd);

    Optional<ProductDto> opt2 = storage.loadProductById(id);
    assertTrue(opt2.isPresent(), () -> String.format("Expected to find Product with id {%s}", id));
    ProductDto dto = opt2.get();
    assertEquals("Cheddar Cheese", dto.getName(),
        "ProductDto didn't have correct name.");
    assertEquals("sku123", dto.getExternalId(),
        "ProductDto didn't have correct externalId.");
    assertEquals("0.65", dto.getItemPrice(),
        "ProductDto didn't have correct unit price.");
    assertEquals("Ounces", dto.getUnits(),
        "ProductDto didn't have correct units.");
  }

  @Test
  @DisplayName("Should causing an illegal argument exception when category isn't found")
  void missing_category() {
    CreateProductCommand cmd = new CreateProductCommand();
    cmd.setCategoryId("c6");

    assertThrows(IllegalArgumentException.class, () -> storage.createProduct(cmd)
        ,"No exception was thrown for an invalid category");
  }

  @Test
  @DisplayName("Should be able to receive a shipment of products")
  void receive_shipment() {
    ReceiveShipmentCommand cmd = new ReceiveShipmentCommand();

    assertTrue(storage.receiveShipmentOfProducts(cmd));
  }

  @Test
  @DisplayName("Shouldn't allow shipment to be received twice")
  void receive_shipment_twice() {
    ReceiveShipmentCommand cmd = new ReceiveShipmentCommand();
    cmd.setShipmentId("s1");

    assertTrue(storage.receiveShipmentOfProducts(cmd));
    assertThrows(IllegalArgumentException.class, () -> storage.receiveShipmentOfProducts(cmd));
  }

  @Test
  @DisplayName("Can receive a shipment with items")
  void receive_shipment_with_items() {
    ReceiveShipmentCommand cmd = new ReceiveShipmentCommand();
    cmd.setShipmentId("s1");
    cmd.addItem(
        new Item().setId("item123")
        .setName("Cheddar Cheese")
        .setUnits("ounces")
        .setItemsPerCase("4")
        .setItemPrice("0.56")
        .setCasePrice("0.00")
        .setNumberOfCases("0")
        .setNumberOfItems("4"));
    cmd.addItem(
        new Item().setId("item234")
            .setName("Chicken Breast")
            .setUnits("ounces")
            .setItemsPerCase("4")
            .setItemPrice("0.56")
            .setCasePrice("0.00")
            .setNumberOfCases("0")
            .setNumberOfItems("4"));

    assertTrue(storage.receiveShipmentOfProducts(cmd));
  }

  @Test
  @DisplayName("Should create a new product when one doesn't exist")
  void creates_new_product() {
    ReceiveShipmentCommand cmd = new ReceiveShipmentCommand();
    cmd.setShipmentId("s1");
    cmd.addItem(new Item()
        .setId("item123")
        .setName("Cheddar Cheese")
        .setUnits("ounces")
        .setItemsPerCase("4")
        .setItemPrice("0.56")
        .setCasePrice("0.00")
        .setNumberOfCases("0")
        .setNumberOfItems("4"));

    assertTrue(storage.receiveShipmentOfProducts(cmd));
    assertTrue(storage.loadAllActiveProducts()
        .stream()
        .anyMatch(p -> p.getExternalId().equals("item123"))
    );
  }

  @Test
  @DisplayName("Receiving a shipment should update the quantity in-stock for a product")
  void updates_in_stock() {
    ReceiveShipmentCommand cmd = new ReceiveShipmentCommand();
    cmd.setShipmentId("s1");
    cmd.addItem(new Item()
        .setId("item1")
        .setName("Cheddar Cheese")
        .setUnits("ounces")
        .setItemsPerCase("4")
        .setItemPrice("0.56")
        .setCasePrice("0.00")
        .setNumberOfCases("0")
        .setNumberOfItems("4"));

    assertTrue(storage.receiveShipmentOfProducts(cmd));
    Optional<ProductDto> opt = storage.loadProductByExternalId("item1");
    assertTrue(opt.isPresent());
    ProductDto dto = opt.get();
    assertEquals("4", dto.getQtyOnHand());
  }

  @Test
  @DisplayName("Should be able to load a product by external id")
  void load_by_external() {
    assertTrue(storage.loadProductByExternalId("item1").isPresent());
  }

  private ProductDto.ProductModelBuilder getDefaultItem() {
    return ProductDto.builder()
        .withName("Bread")
        .withCategory("Dry Goods")
        .withUnitOfMeasurement("case")
        .withItemPrice("5.99")
        .withCasePrice("20.99");
  }
}
