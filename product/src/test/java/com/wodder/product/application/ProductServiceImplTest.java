package com.wodder.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.product.domain.model.product.Category;
import com.wodder.product.domain.model.product.Location;
import com.wodder.product.domain.model.product.Product;
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
            psf.getRepository(Product.class),
            psf.getRepository(Category.class),
            psf.getRepository(Location.class));
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
    Optional<ProductDto> result = storage.loadProduct(newItem.getId());
    assertTrue(result.isPresent());
  }

  @Test
  @DisplayName("Can update an item's location")
  void update_location() {
    ProductDto newItem =
        storage.createNewProduct(getDefaultItem().withLocation("Freezer").build()).get();
    ProductDto result = storage.updateProductLocation(newItem.getId(), "Pantry").get();
    assertEquals(result.getLocation(), "Pantry");
  }

  @Test
  @DisplayName("Id is required to load item")
  void read_item_bo_id() {
    Optional<ProductDto> item = storage.loadProduct(null);
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
        storage.createNewProduct(getDefaultItem().withLocation("Freezer").build()).get();
    ProductDto result =
        storage.updateProductUnitOfMeasurement(newItem.getId(), "Gallons", 4).get();
    assertEquals("Gallons", result.getUnits());
    assertEquals(4, result.getUnitsPerCase());
  }

  @Test
  @DisplayName("If item is not present then empty is returned")
  void item_not_found() {
    Optional<ProductDto> opt = storage.updateProductUnitOfMeasurement("1", "Gallons", 4);
    assertTrue(opt.isEmpty());
  }

  @Test
  @DisplayName("Can update the price for an item")
  void update_price() {
    ProductDto newItem =
        storage.createNewProduct(getDefaultItem().withLocation("Freezer").build()).get();
    ProductDto result = storage.updateProductPrice(newItem.getId(), "0.68", "19.23").get();
    assertEquals("0.68", result.getItemPrice());
    assertEquals("19.23", result.getCasePrice());
  }

  private ProductDto.ProductModelBuilder getDefaultItem() {
    return ProductDto.builder()
        .withName("Bread")
        .withLocation("Pantry")
        .withCategory("Dry Goods")
        .withUnitOfMeasurement("case")
        .withItemPrice("5.99")
        .withCasePrice("20.99");
  }
}
