package com.wodder.product.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.wodder.product.domain.model.category.Category;
import com.wodder.product.domain.model.product.CasePack;
import com.wodder.product.domain.model.product.Price;
import com.wodder.product.domain.model.product.Product;
import com.wodder.product.domain.model.product.ProductName;
import com.wodder.product.domain.model.product.Quantity;
import com.wodder.product.domain.model.product.UnitOfMeasurement;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostgresProductRepositoryIT extends BasePostgresTest {

  PostgresProductRepository repo;
  PostgresCategoryRepository cat;
  Category category;

  @BeforeEach
  void setUp() {
    repo = new PostgresProductRepository(ds);
    cat = new PostgresCategoryRepository(ds);

    category = Category.of("Dairy");
    cat.createItem(category);
  }

  @AfterEach
  void tearDown() {
    try (Connection c = ds.getConnection();
        Statement s = c.createStatement();
    ) {
      assertFalse(s.execute("DELETE FROM product.products"), "Didn't expect a result set");
      assertFalse(s.execute("DELETE FROM product.categories"), "Didn't expect a result set");
    } catch (Exception e) {
      fail("Couldn't clean up after test method.");
    }
  }

  @Test
  @DisplayName("Should be able to save a product")
  void create_product() {

    Product p = Product.builder("Gravy")
        .build();
    repo.createItem(p);

    assertNotNull(p.getDatabaseId());
  }

  @Test
  @DisplayName("Should be able to load a product")
  void load() {
    Category c = Category.of("Dairy");
    cat.createItem(c);

    Product p = Product.builder("2% Milk")
        .withCategory(c)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    repo.createItem(p);

    Optional<Product> opt = repo.loadById(p.getId());
    assertTrue(opt.isPresent(), "Optional was empty");
    Product result = opt.get();
    assertEquals(p, result, "Retrieved product didn't match created product.");
    assertEquals(c, result.getCategory());
    assertEquals("e123", result.getExternalId().getValue());
    assertEquals(Price.of("3.99"), result.getCasePrice());
    assertEquals(Price.of("5.99"), result.getUnitPrice());
    assertEquals("Gallons", result.getUnits().getUnit());
    assertTrue(result.isActive());
    assertEquals(Quantity.of("2"), result.getQtyOnHand());
    assertEquals(CasePack.ofItemsPerCase("4"), result.getCasePack());
  }

  @Test
  @DisplayName("Should be able to load a product by object")
  void load_by_object() {
    Category c = Category.of("Dairy");
    cat.createItem(c);

    Product p = Product.builder("2% Milk")
        .withCategory(c)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    repo.createItem(p);

    Optional<Product> opt = repo.loadByItem(p);
    assertTrue(opt.isPresent(), "Optional was empty");
    Product result = opt.get();
    assertEquals(p, result, "Retrieved product didn't match created product.");
    assertEquals(c, result.getCategory());
    assertEquals("e123", result.getExternalId().getValue());
    assertEquals(Price.of("3.99"), result.getCasePrice());
    assertEquals(Price.of("5.99"), result.getUnitPrice());
    assertEquals("Gallons", result.getUnits().getUnit());
    assertTrue(result.isActive());
    assertEquals(Quantity.of("2"), result.getQtyOnHand());
    assertEquals(CasePack.ofItemsPerCase("4"), result.getCasePack());
  }

  @Test
  @DisplayName("Should be able to update product's name")
  void update_name() {
    Product p = Product.builder("2% Milk")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    repo.createItem(p);

    ProductName expected = ProductName.of("2 percent milk");
    p.updateName(expected);

    repo.updateItem(p);

    Optional<Product> opt = repo.loadById(p.getId());
    assertTrue(opt.isPresent());
    Product result = opt.get();
    assertEquals(expected, result.getName());
  }

  @Test
  @DisplayName("Should be able to update product's item price")
  void update_item_price() {
    Product p = Product.builder("2% Milk")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    repo.createItem(p);

    Price expected = Price.of("2.99");
    p.updateUnitPrice(expected);

    repo.updateItem(p);

    Optional<Product> opt = repo.loadById(p.getId());
    assertTrue(opt.isPresent());
    Product result = opt.get();
    assertEquals(expected, result.getUnitPrice());
  }

  @Test
  @DisplayName("Should be able to update product's case price")
  void update_case_price() {
    Product p = Product.builder("2% Milk")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    repo.createItem(p);

    Price expected = Price.of("10.99");
    p.updateCasePrice(expected);

    repo.updateItem(p);

    Optional<Product> opt = repo.loadById(p.getId());
    assertTrue(opt.isPresent());
    Product result = opt.get();
    assertEquals(expected, result.getCasePrice());
  }

  @Test
  @DisplayName("Should be able to update product's units")
  void update_units() {
    Product p = Product.builder("2% Milk")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    repo.createItem(p);

    UnitOfMeasurement expected = UnitOfMeasurement.of("Fluid Ounces");
    p.updateUnitOfMeasurement(expected);

    repo.updateItem(p);

    Optional<Product> opt = repo.loadById(p.getId());
    assertTrue(opt.isPresent());
    Product result = opt.get();
    assertEquals(expected, result.getUnits());
  }

  @Test
  @DisplayName("Should be able to update product's active status")
  void update_active() {
    Product p = Product.builder("2% Milk")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    repo.createItem(p);

    p.inactivate();

    repo.updateItem(p);

    Optional<Product> opt = repo.loadById(p.getId());
    assertTrue(opt.isPresent());
    Product result = opt.get();
    assertFalse(result.isActive());
  }

  @Test
  @DisplayName("Should be able to update product's quantity on hand")
  void update_qty() {
    Product p = Product.builder("2% Milk")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    repo.createItem(p);

    Quantity qty = Quantity.of("2");
    p.receiveQty(qty);
    repo.updateItem(p);

    Optional<Product> opt = repo.loadById(p.getId());
    assertTrue(opt.isPresent());
    Product result = opt.get();
    assertEquals(4, result.getQtyOnHand().getAmount());
  }

  @Test
  @DisplayName("Should be able to update product's case pack")
  void update_case_pack() {
    Product p = Product.builder("2% Milk")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    repo.createItem(p);

    CasePack expected = CasePack.ofItemsPerCase("1");
    p.updateCasePack(expected);
    repo.updateItem(p);

    Optional<Product> opt = repo.loadById(p.getId());
    assertTrue(opt.isPresent());
    Product result = opt.get();
    assertEquals(expected, result.getCasePack());
  }

  @Test
  @DisplayName("Should be able to load product by external id")
  void load_by_ext_id() {
    Category c = Category.of("Dairy");
    cat.createItem(c);

    Product p = Product.builder("2% Milk")
        .withCategory(c)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    repo.createItem(p);

    Optional<Product> opt = repo.loadByExternalId(p.getExternalId());
    assertTrue(opt.isPresent(), "Optional was empty");
    Product result = opt.get();
    assertEquals(p, result, "Retrieved product didn't match created product.");
    assertEquals(c, result.getCategory());
    assertEquals("e123", result.getExternalId().getValue());
    assertEquals(Price.of("3.99"), result.getCasePrice());
    assertEquals(Price.of("5.99"), result.getUnitPrice());
    assertEquals("Gallons", result.getUnits().getUnit());
    assertTrue(result.isActive());
    assertEquals(Quantity.of("2"), result.getQtyOnHand());
    assertEquals(CasePack.ofItemsPerCase("4"), result.getCasePack());
  }

  @Test
  @DisplayName("Should be able to delete a product")
  void delete() {
    Category c = Category.of("Dairy");
    cat.createItem(c);

    Product p = Product.builder("2% Milk")
        .withCategory(c)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    repo.createItem(p);

    assertTrue(repo.deleteItem(p.getId()));
    assertTrue(repo.loadById(p.getId()).isEmpty());
  }

  @Test
  @DisplayName("Should be able to delete a product by object")
  void delete_obj() {
    Category c = Category.of("Dairy");
    cat.createItem(c);

    Product p = Product.builder("2% Milk")
        .withCategory(c)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    repo.createItem(p);

    assertTrue(repo.deleteItem(p));
    assertTrue(repo.loadById(p.getId()).isEmpty());
  }

  @Test
  @DisplayName("Should be able to load all products")
  void load_all_products() {
    Product p = Product.builder("2% Milk")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    Product p1 = Product.builder("Cheese")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    Product p2 = Product.builder("Pine Sol")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();

    repo.createItem(p);
    repo.createItem(p1);
    repo.createItem(p2);


    List<Product> items = repo.loadAllItems();
    assertFalse(items.isEmpty());
    assertEquals(3, items.size());
  }

  @Test
  @DisplayName("Should be able to load all active products")
  void load_all_active() {
    Product p = Product.builder("2% Milk")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();
    Product p1 = Product.builder("Cheese")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(false)
        .build();
    Product p2 = Product.builder("Pine Sol")
        .withCategory(category)
        .withExternalId("e123")
        .withUnitsOfMeasurement("Gallons")
        .withCasePrice(Price.of("3.99"))
        .withUnitPrice(Price.of("5.99"))
        .withStockedCount(Quantity.of("2"))
        .withCasePack("4")
        .isActive(true)
        .build();

    repo.createItem(p);
    repo.createItem(p1);
    repo.createItem(p2);


    List<Product> items = repo.loadActiveItems();
    assertFalse(items.isEmpty());
    assertEquals(2, items.size());
  }
}
