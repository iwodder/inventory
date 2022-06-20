package com.wodder.inventory.domain.model.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.wodder.inventory.domain.model.product.UnitOfMeasurement;
import com.wodder.inventory.dto.InventoryDto;
import com.wodder.inventory.dto.InventoryItemDto;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
  @DisplayName("Can an add item to inventory")
  @Disabled
  void add_item() {
    Inventory inventory = Inventory.emptyInventory();
    Item item = Item.builder()
        .withName("2% Milk")
        .withLocation("Refrigerator")
        .withUnits("Gallon", "4")
        .build();

    inventory.addItemToInventory(item);

    assertEquals(1, inventory.numberOfItems());
  }

  @Test
  @DisplayName("Copy constructor makes a deep copy of counts")
  void deep_copy() {
    Inventory i =
        Inventory.inventoryWith(
            Arrays.asList(
                Item.builder()
                    .withName("2% Milk")
                    .withLocation("Refrigeator")
                    .withUnits("Gallon", "4")
                    .build(),
                Item.builder()
                    .withName("Chicken Breast")
                    .withLocation("Refrigerator")
                    .withUnits("Pound", "4")
                    .build()));


    Inventory i2 = new Inventory(i);

    assertNotSame(i, i2);
    assertEquals(i.numberOfItems(), i2.numberOfItems());
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
                StorageLocation.of("Refrigerator"),
                new UnitOfMeasurement("Gallon", 4))));
    model.addInventoryItem(
        new InventoryItemDto(
            new Item(
                ItemId.newId(),
                "Shredded Cheese",
                StorageLocation.of("Refrigerator"),
                new UnitOfMeasurement("Gallon", 4))));
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
        () -> i.updateInventoryCount(
            "2% Milk", "Refrigerator", Count.countFrom("3", "4")));
  }

  @Test
  @DisplayName("Can query inventory items by location")
  void query_by_location() {
    Inventory i =
        Inventory.inventoryWith(
            List.of(
                Item.builder()
                    .withName("2% Milk")
                    .withLocation("Freezer")
                    .withUnits("Gallon", "4")
                    .build()
            ));
    assertEquals(1, i.getItemsByLocation(StorageLocation.of("Freezer")).size());
    assertEquals(0, i.getItemsByLocation(StorageLocation.of("Pantry")).size());
  }

  @Test
  @DisplayName("Should be able to query item by name")
  void query_by_name() {
    Item item = Item.builder()
        .withName("2% Milk")
        .withLocation("Refrigerator")
        .withUnits("Gallon", "4")
        .build();

    Inventory inventory = Inventory.emptyInventory();
    inventory.addItemToInventory(item);

    assertEquals(item, inventory.getItem("2% Milk"));
  }

  @Test
  @DisplayName("Should be able to retrieve all items")
  void query_all() {
    Item item = Item.builder()
        .withName("2% Milk")
        .withLocation("Refrigerator")
        .withUnits("Gallon", "4")
        .build();
    Item item2 = Item.builder()
        .withName("Chocolate Milk")
        .withLocation("Refrigerator")
        .withUnits("Gallon", "2")
        .build();
    Inventory inventory =
        Inventory.inventoryWith(
            List.of(item, item2)
        );

    assertEquals(2, inventory.numberOfItems());
  }

  @Test
  @DisplayName("Item not in inventory should return empty optional")
  void query_by_name_absent() {
    Inventory inventory = Inventory.emptyInventory();

    assertEquals(Item.empty(), inventory.getItem("2% Milk"));
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

  @Test
  @DisplayName("Inventory starts with zero counts for all items")
  void zeroCount() {
    Item i = Item.builder().withName("Milk").withLocation("Refrigerator").build();
    Item i2 = Item.builder().withName("Cheese").withLocation("Freezer").build();
    Item i3 = Item.builder().withName("Soap").withLocation("Dry Storage").build();

    List<Item> items = List.of(i, i2, i3);
    Inventory inv = Inventory.inventoryWith(items);

    assertEquals(Count.ofZero(), inv.getCount("Milk").or(() -> fail("Expected to find Milk")));
    assertEquals(Count.ofZero(), inv.getCount("Cheese").or(() -> fail("Expected to find Cheese")));
    assertEquals(Count.ofZero(), inv.getCount("Soap").or(() -> fail("Expected to find Soap")));
  }

  @Test
  @DisplayName("Inventory returns the total count for an item in multiple locations")
  void totalCount() {
    Item i = Item.builder().withName("Milk").withLocation("Refrigerator").build();
    Item i2 = Item.builder().withName("Milk").withLocation("Freezer").build();

    List<Item> items = List.of(i, i2);
    Inventory inv = Inventory.inventoryWith(items);

    assertEquals(Count.ofZero(), inv.getCount("Milk").or(() -> fail("Expected to find Milk")));
  }
}
