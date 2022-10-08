package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.Entity;
import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.inventory.Item;

public class TestPersistenceFactory implements PersistenceFactory {
  private final InMemoryInventoryRepository inventoryRepository;
  private final InMemoryItemRepository itemRepository;

  private TestPersistenceFactory() {
    inventoryRepository = new InMemoryInventoryRepository();
    itemRepository = new InMemoryItemRepository();
  }

  public static PersistenceFactory getUnpopulated() {
    return new TestPersistenceFactory();
  }

  public static PersistenceFactory getPopulated() {
    TestPersistenceFactory t = new TestPersistenceFactory();
    t.populateWithData();
    return t;
  }

  @Override
  public InventoryRepository getInventoryRepository() {
    return inventoryRepository;
  }

  @Override
  public <T extends Entity<U>, U> Repository<T, U> getRepository(Class<T> clazz) {
    if (clazz.isAssignableFrom(Inventory.class)) {
      @SuppressWarnings("unchecked")
      Repository<T, U> c = (Repository<T, U>) inventoryRepository;
      return c;
    } else if (clazz.isAssignableFrom(Item.class)) {
      @SuppressWarnings("unchecked")
      Repository<T, U> c = (Repository<T, U>) itemRepository;
      return c;
    }
    return null;
  }

  private void populateWithData() {
    itemRepository.createItem(
        Item.builder()
            .withId("item123")
            .withName("2% Milk")
            .withProductId("p123")
            .withLocation("Refrigerator")
            .withUnits("Gallon")
            .build()
    );
    itemRepository.createItem(
        Item.builder()
            .withId("item234")
            .withName("Fabric Softener")
            .withLocation("Laundry Room")
            .withUnits("Gallon")
            .build()
    );
    itemRepository.createItem(
        Item.builder()
            .withId("item345")
            .withName("Chicken Breast")
            .withLocation("Refrigerator")
            .withUnits("Pounds")
            .build()
    );
  }
}
