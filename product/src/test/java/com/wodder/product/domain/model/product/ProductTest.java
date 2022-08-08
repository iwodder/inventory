package com.wodder.product.domain.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wodder.product.domain.model.category.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

  @Test
  @DisplayName("Identical values are equal")
  void inventory_item_is_equal_based_on_desc() {
    Product inv = new Product("2% Milk", new Category("Dry Goods"));
    Product inv2 = new Product("2% Milk", new Category("Chemicals"));
    assertEquals(inv, inv2);
  }

  @Test
  @DisplayName("Name is required for creating an item")
  void name_required() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Product.builder(ProductId.generateId(), null).build();
        });
  }

  @Test
  @DisplayName("Name cannot be blank")
  void blank_name() {
    assertThrows(
        IllegalArgumentException.class,
        () -> Product.builder(ProductId.generateId(), ProductName.of("")).build());
  }

  @Test
  @DisplayName("Category is required")
  void null_category() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Product i = new Product("Bread", null);
        });
  }

  @Test
  @DisplayName("Can successfully update a category")
  void update_category() {
    Product i = new Product("Bread", new Category("Dry Goods"));
    Category newCategory = new Category("Refrigerated");
    i.updateCategory(newCategory);
    assertEquals(newCategory, i.getCategory());
  }

  @Test
  @DisplayName("Trying to update with the same category causes IllegalArgumentException")
  void update_same_category() {
    final Product i = new Product("Bread", new Category("Dry Goods"));
    assertThrows(IllegalArgumentException.class, () -> i.updateCategory(new Category("Dry Goods")));
  }

  @Test
  @DisplayName("Inventory Item can be created with a Unit of Measurement")
  void has_uom() {
    UnitOfMeasurement uom = new UnitOfMeasurement("Loaves", 4);
    Product i =
        Product.builder(ProductId.generateId(), ProductName.of("Bread"))
            .withCategory(Category.of("Dry Goods"))
            .withUnitsOfMeasurement(uom)
            .build();
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
            new UnitOfMeasurement("Loaves", 4));
    UnitOfMeasurement newUom = new UnitOfMeasurement("Slices", 1200);
    i.updateUnitOfMeasurement(newUom);
    assertEquals(newUom, i.getUnits());
    assertEquals(1200, i.getUnitsPerCase());
  }

  @Test
  @DisplayName("Product can be created with a price")
  void has_price() {
    Product i =
        Product.builder(ProductId.generateId(), ProductName.of("Bread"))
            .withCategory(Category.of("Dry Goods"))
            .withUnitsOfMeasurement(UnitOfMeasurement.of("Loaves", "4"))
            .withUnitPrice(Price.of("0.99"))
            .withCasePrice(Price.of("3.96"))
            .build();

    assertEquals(Price.of("0.99"), i.getUnitPrice());
    assertEquals(Price.of("3.96"), i.getCasePrice());
  }

  @Test
  @DisplayName("Can successfully update an item's price")
  void update_price() {
    Product i =
        Product.builder(ProductId.generateId(), ProductName.of("Bread"))
            .withCategory(Category.of("Dry Goods"))
            .withUnitsOfMeasurement(UnitOfMeasurement.of("Loaves", "4"))
            .withUnitPrice(Price.of("0.99"))
            .withCasePrice(Price.of("3.96"))
            .build();

    i.updateUnitPrice(Price.of("1.99"));
    i.updateCasePrice(Price.of("10.96"));
    assertEquals(Price.of("1.99"), i.getUnitPrice());
    assertEquals(Price.of("10.96"), i.getCasePrice());
  }

  @Test
  @DisplayName("Can successfully add received quantity")
  void receive_qty() {
    Product p = Product.from(
        ExternalId.of("e123"),
        "Cheese",
        Category.defaultCategory(),
        UnitOfMeasurement.of("ounces", "4"),
        Price.of("0.50")
    );

    p.receiveQty(Quantity.of("1"));

    assertEquals(Quantity.of("1"), p.getQtyOnHand());
  }

  @Test
  @DisplayName("Receiving a quantity adds to the current quantity")
  void receive_qty_adds() {
   Product p = new Product(
              ProductId.productIdOf("p123"),
              ExternalId.of("e123"),
          "Cheese",
              Category.defaultCategory(),
              UnitOfMeasurement.of("Ounces", "5"),
              Price.of("0.50"),
              Quantity.of("1")
       );

   p.receiveQty(Quantity.of("1"));

   assertEquals(Quantity.of("2"), p.getQtyOnHand());
  }
}
