package com.wodder.inventory.domain.model.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.inventory.domain.model.product.Price;
import com.wodder.inventory.domain.model.product.UnitOfMeasurement;
import com.wodder.inventory.dto.InventoryDto;
import com.wodder.inventory.dto.InventoryItemDto;
import com.wodder.inventory.dto.ProductDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InventoryTest {

  @Test
  @DisplayName("Inventory is created with current date")
  void current_date() {
    Inventory inventory = Inventory.emptyInventory();
    assertEquals(LocalDate.now(), inventory.getInventoryDate());
  }

  @Test
  @DisplayName("Can add count to inventory")
  @Disabled
  void add_item() {
    Inventory inventory = Inventory.emptyInventory();
    Item item =
        new Item(
            ItemId.newId(),
            "2% Milk",
            InventoryLocation.of("Refrigerator"),
            InventoryCategory.of("Dairy"),
            new UnitOfMeasurement("Gallon", 4),
            new Price("0.99", "4.98"),
            new InventoryCount(1.0, 0.25));
    inventory.addItemToInventory(item);
    assertEquals(1, inventory.numberOfItems());
  }

  @Test
  @DisplayName("Copy constructor makes a deep copy of counts")
  void deep_copy() {
    Inventory i =
        Inventory.createNewInventoryWithProducts(
            Arrays.asList(
                ProductDto.builder()
                    .withName("2% Milk")
                    .withCategory("Dairy")
                    .withLocation("Refrigerator")
                    .withUnitOfMeasurement("Gallon")
                    .withItemsPerCase(4)
                    .withItemPrice("0.99")
                    .withCasePrice("4.98")
                    .build(),
                ProductDto.builder()
                    .withName("Chicken Breast")
                    .withCategory("Meat")
                    .withLocation("Refrigerator")
                    .withUnitOfMeasurement("Pound")
                    .withItemsPerCase(4)
                    .withItemPrice("0.99")
                    .withCasePrice("4.98")
                    .build()));

    Inventory i2 = new Inventory(i);
    assertNotSame(i, i2);
    assertEquals(i.numberOfItems(), i2.numberOfItems());
    InventoryCount count1 = i.getCount("2% Milk").get();
    InventoryCount count2 = i2.getCount("2% Milk").get();
    assertNotSame(count1, count2);
  }

  @Test
  @DisplayName("Can create an Inventory from InventoryModel")
  void from_model() {
    InventoryDto model = new InventoryDto();
    model.setInventoryDate(LocalDate.now());
    model.addInventoryItem(
        new InventoryItemDto(
            new Item(
                ItemId.newId(),
                "2% Milk",
                InventoryLocation.of("Refrigerator"),
                InventoryCategory.of("Dairy"),
                new UnitOfMeasurement("Gallon", 4),
                new Price("0.99", "4.98"),
                new InventoryCount(1.0, 0.25))));
    model.addInventoryItem(
        new InventoryItemDto(
            new Item(
                ItemId.newId(),
                "Shredded Cheese",
                InventoryLocation.of("Refrigerator"),
                InventoryCategory.of("Dairy"),
                new UnitOfMeasurement("Gallon", 4),
                new Price("0.99", "4.98"),
                new InventoryCount(1.0, 0.25))));
    Inventory i = new Inventory(model);

    assertEquals(model.getInventoryDate(), i.getInventoryDate());
    assertEquals(model.numberOfItems(), i.numberOfItems());
  }

  @Test
  @DisplayName("Copy constructor makes sure that InventoryState is set")
  void copy_state() {
    Inventory i = Inventory.emptyInventory();
    Inventory i2 = new Inventory(i);
    assertTrue(i2.isOpen());
  }

  @Test
  @DisplayName("Inventory is created in the OPEN state")
  void open_state() {
    Inventory i = Inventory.emptyInventory();
    assertTrue(i.isOpen());
  }

  @Test
  @DisplayName("Inventory can be closed")
  void closed_state() {
    Inventory i = Inventory.emptyInventory();
    i.finish();
    assertFalse(i.isOpen());
  }

  @Test
  @DisplayName("Adding an item to a closed inventory causes IllegalStateException")
  void add_when_closed() {
    Inventory i = Inventory.emptyInventory();
    i.finish();
    assertThrows(
        IllegalStateException.class,
        () -> i.updateInventoryCount("2% Milk", "Refrigerator", new InventoryCount(3, 4)));
  }

  @Test
  @DisplayName("Can add product to inventory")
  void add_product() {
    Inventory i =
        Inventory.createNewInventoryWithProducts(
            List.of(
                ProductDto.builder()
                    .withName("2% Milk")
                    .withCategory("Dairy")
                    .withLocation("Refrigerator")
                    .withUnitOfMeasurement("Gallon")
                    .withItemsPerCase(4)
                    .withItemPrice("0.99")
                    .withCasePrice("4.98")
                    .build()
            ));
    assertEquals(1, i.numberOfItems());
  }

  @Test
  @DisplayName("Can query inventory items by location")
  void query_by_location() {
    Inventory i =
        Inventory.createNewInventoryWithProducts(
            List.of(
                ProductDto.builder()
                    .withName("2% Milk")
                    .withCategory("Dairy")
                    .withLocation("Freezer")
                    .withUnitOfMeasurement("Gallon")
                    .withItemsPerCase(4)
                    .withItemPrice("0.99")
                    .withCasePrice("4.98")
                    .build()
            ));
    assertEquals(1, i.getItemsByLocation(InventoryLocation.of("Freezer")).size());
    assertEquals(0, i.getItemsByLocation(InventoryLocation.of("Pantry")).size());
  }

  @Test
  @DisplayName("Can query inventory items by category")
  void query_by_category() {
    Inventory i =
        Inventory.createNewInventoryWithProducts(
            List.of(
                ProductDto.builder()
                    .withName("2% Milk")
                    .withCategory("Frozen")
                    .withLocation("Freezer")
                    .withUnitOfMeasurement("Gallon")
                    .withItemsPerCase(4)
                    .withItemPrice("0.99")
                    .withCasePrice("4.98")
                    .build()
            ));
    assertEquals(1, i.getItemsByCategory(InventoryCategory.of("Frozen")).size());
    assertEquals(0, i.getItemsByCategory(InventoryCategory.of("Dairy")).size());
  }

  @Test
  @DisplayName("Should be able to query item by name")
  void query_by_name() {
    Item item = Item.builder()
        .withName("2% Milk")
        .withLocation("Refrigerator")
        .withCategory("Dairy")
        .withUnits("Gallon", "4")
        .withPricing("0.99", "4.98")
        .withCount("1.0", "0.25")
        .build();

    Inventory inventory = Inventory.emptyInventory();
    inventory.addItemToInventory(item);

    assertEquals(item, inventory.getItem("2% Milk"));
  }

  @Test
  @DisplayName("Should be able to retrieve all items")
  void query_all() {
    Item item = Item.builder()
        .withCategory("Dairy")
        .withLocation("Refrigerator")
        .withName("2% Milk")
        .withUnits("Gallon", "4")
        .withPricing("0.99", "4.98")
        .withCount("1.0", "0.25")
        .build();
    Item item2 = Item.builder()
        .withCategory("Dairy")
        .withLocation("Refrigerator")
        .withName("Chocolate Milk")
        .withUnits("Gallon", "2")
        .withPricing("1.99", "4.98")
        .withCount("1.0", "0.25")
        .build();
    Inventory inventory = Inventory.emptyInventory();
    inventory.addItemToInventory(item);
    inventory.addItemToInventory(item2);

    Iterable<Item> items = inventory.getItems();
    AtomicInteger cnt = new AtomicInteger();
    items.forEach((i) -> cnt.getAndIncrement());

    assertEquals(2, cnt.get());
  }

  @Test
  @DisplayName("Absent item in inventory should return an empty item")
  void query_by_name_absent() {
    Inventory inventory = Inventory.emptyInventory();

    assertEquals(Item.empty(), inventory.getItem("2% Milk"));
  }

  @Test
  @DisplayName("Should create an inventory with just item ids")
  void newInventory() {
    List<ItemId> itemIds = new ArrayList<>();
    itemIds.add(ItemId.of("123"));
    itemIds.add(ItemId.of("234"));
    itemIds.add(ItemId.of("345"));

    Inventory i = Inventory.createNewInventory(itemIds);

    assertEquals(3, i.numberOfItems());
  }

  @Test
  @DisplayName("Should be able to create an empty inventory")
  void emptyInventory() {
    Inventory i = Inventory.emptyInventory();

    assertNotNull(i);
    assertEquals(LocalDate.now(), i.getInventoryDate());
    assertEquals(0, i.numberOfItems());
    assertTrue(i.isOpen());
  }
}
