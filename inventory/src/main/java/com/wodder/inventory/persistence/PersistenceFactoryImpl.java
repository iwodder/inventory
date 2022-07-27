package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.Entity;
import com.wodder.inventory.domain.model.inventory.Inventory;

public class PersistenceFactoryImpl implements PersistenceFactory {

  private final InMemoryInventoryRepository inventoryRepository;

  public PersistenceFactoryImpl() {
    inventoryRepository = new InMemoryInventoryRepository();
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
    }
    return null;
  }
}
