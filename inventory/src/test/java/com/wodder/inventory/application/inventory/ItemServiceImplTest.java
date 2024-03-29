package com.wodder.inventory.application.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.wodder.inventory.domain.model.inventory.Item;
import com.wodder.inventory.dto.ItemDto;
import com.wodder.inventory.persistence.PersistenceFactory;
import com.wodder.inventory.persistence.TestPersistenceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemServiceImplTest {

  PersistenceFactory tpf;

  @BeforeEach
  void setup() {
    tpf = TestPersistenceFactory.getUnpopulated();
  }

  @Test
  void addItem() {
    ItemServiceImpl itemService = new ItemServiceImpl(tpf.getRepository(Item.class));

    String id = itemService.addItem(
        "p123",
        "2% Milk",
        "Refrigerator",
        "Gallons"
    );

    ItemDto item = itemService.loadItem(id).orElseGet(() -> fail("Couldn't locate item by id"));
    assertNotEquals("", id);
    assertEquals("2% Milk", item.getName());
    assertEquals("Refrigerator", item.getLocation());
    assertEquals("p123", item.getProductId());
  }

  @Test
  void deleteItem() {
    ItemServiceImpl itemService = new ItemServiceImpl(tpf.getRepository(Item.class));
    String id = itemService.addItem(
        "p123",
        "2% Milk",
        "Refrigerator",
        "Gallons"
    );

    itemService.deleteItem(id);

    assertTrue(itemService.loadItem(id).isEmpty());
  }

  @Test
  void moveItem() {
    ItemServiceImpl itemService = new ItemServiceImpl(tpf.getRepository(Item.class));
    String id = itemService.addItem(
        "p123",
        "2% Milk",
        "Refrigerator",
        "Gallons"
    );

    ItemDto result = itemService.moveItem(id, "Pantry")
        .orElseGet(() -> fail("Expected to get present value, but it was missing."));

    assertEquals("Pantry", result.getLocation());
  }

  @Test
  void duplicateItem() {
    ItemServiceImpl itemService = new ItemServiceImpl(tpf.getRepository(Item.class));
    String id = itemService.addItem(
        "p123",
        "2% Milk",
        "Refrigerator",
        "Gallons"
    );

    String duplicateId = itemService.copyItemToNewLocation(id, "Pantry");

    ItemDto dup = itemService.loadItem(duplicateId).orElseGet(() -> fail("Couldn't locate item by duplicateId"));
    ItemDto orig = itemService.loadItem(id).orElseGet(() -> fail("Couldn't locate item by id"));

    assertEquals(orig.getName(), dup.getName());
    assertEquals(orig.getProductId(), dup.getProductId());
    assertNotEquals(orig.getLocation(), dup.getLocation());
  }

  @Test
  @DisplayName("Should be able to load all items")
  void load_all() {
    ItemServiceImpl itemService = new ItemServiceImpl(tpf.getRepository(Item.class));
    itemService.addItem(
        "p123",
        "2% Milk",
        "Refrigerator",
        "Gallons"
    );
    itemService.addItem(
        "p234",
        "2% Milk",
        "Refrigerator",
        "Gallons"
    );

    assertEquals(2, itemService.getAllItems().size());
  }

  @Test
  @DisplayName("No items should return an empty list")
  void load_all_empty() {
    ItemServiceImpl itemService = new ItemServiceImpl(tpf.getRepository(Item.class));

    assertEquals(0, itemService.getAllItems().size());
  }
}
