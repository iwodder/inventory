package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.Entity;

public interface PersistenceFactory {

  InventoryRepository getInventoryRepository();

  <T extends Entity<U>, U> Repository<T, U> getRepository(Class<T> clazz);
}
