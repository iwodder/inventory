package com.wodder.product.domain.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

  @Test
  @DisplayName("Identical values are equal")
  void inventory_item_is_equal_based_on_desc() {
    Product inv = new Product("2% Milk", new Category("Dry Goods"), new Location("pantry"));
    Product inv2 = new Product("2% Milk", new Category("Chemicals"), new Location("laundry"));
    assertEquals(inv, inv2);
  }

  @Test
  @DisplayName("Name is required for creating an item")
  void name_required() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Product i = new Product(null, new Category("Dry Goods"), new Location("Pantry"));
        });
  }

  @Test
  @DisplayName("Name cannot be blank")
  void blank_name() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Product i = new Product("", new Category("Dry Goods"), new Location("Pantry"));
        });
  }

  @Test
  @DisplayName("Category is required")
  void null_category() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Product i = new Product("Bread", null, new Location("Pantry"));
        });
  }

  @Test
  @DisplayName("Can successfully update a category")
  void update_category() {
    Product i = new Product("Bread", new Category("Dry Goods"), new Location("Pantry"));
    Category newCategory = new Category("Refrigerated");
    i.updateCategory(newCategory);
    assertEquals(newCategory, i.getCategory());
  }

  @Test
  @DisplayName("Trying to update with the same category causes IllegalArgumentException")
  void update_same_category() {
    final Product i = new Product("Bread", new Category("Dry Goods"), new Location("Pantry"));
    assertThrows(IllegalArgumentException.class, () -> i.updateCategory(new Category("Dry Goods")));
  }

  @Test
  @DisplayName("Inventory Item can be created with a Unit of Measurement")
  void has_uom() {
    UnitOfMeasurement uom = new UnitOfMeasurement("Loaves", 4);
    Product i =
        new Product(
            ProductId.generateId(),
            "Bread",
            new Category("Dry Goods"),
            new Location("Pantry"),
            uom);
    assertEquals(uom, i.getUnits());
    assertEquals(4, i.getUnitsPerCase());
  }

  @Test
  @DisplayName("Can successfully update the unit of measurement")
  void update_uom() {
    Product i =
        new Product(
            ProductId.generateId(),
            "Bread",
            new Category("Dry Goods"),
            new Location("Pantry"),
            new UnitOfMeasurement("Loaves", 4));
    UnitOfMeasurement newUom = new UnitOfMeasurement("Slices", 1200);
    i.updateUnitOfMeasurement(newUom);
    assertEquals(newUom, i.getUnits());
    assertEquals(1200, i.getUnitsPerCase());
  }

  @Test
  @DisplayName("Inventory Item can be created with a price")
  void has_price() {
    Price p = new Price(new BigDecimal("0.99"), new BigDecimal("3.96"));
    Product i =
        new Product(
            ProductId.generateId(),
            "Bread",
            new Category("Dry Goods"),
            new Location("Pantry"),
            new UnitOfMeasurement("Loaves", 4),
            p);
    assertEquals(new BigDecimal("0.99"), i.getUnitPrice());
    assertEquals(new BigDecimal("3.96"), i.getCasePrice());
  }

  @Test
  @DisplayName("Can successfully update an item's price")
  void update_price() {
    Price p = new Price(new BigDecimal("1.99"), new BigDecimal("10.96"));
    Product i =
        new Product(
            ProductId.generateId(),
            "Bread",
            new Category("Dry Goods"),
            new Location("Pantry"),
            new UnitOfMeasurement("Loaves", 4),
            new Price(new BigDecimal("0.99"), new BigDecimal("3.96")));
    i.updatePrice(p);
    assertEquals(new BigDecimal("1.99"), i.getUnitPrice());
    assertEquals(new BigDecimal("10.96"), i.getCasePrice());
  }
}
