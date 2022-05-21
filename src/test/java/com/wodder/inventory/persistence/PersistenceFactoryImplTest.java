package com.wodder.inventory.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.inventory.domain.model.product.Category;
import com.wodder.inventory.domain.model.product.CategoryId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PersistenceFactoryImplTest {

  @Test
  @DisplayName("PersistenceFactory returns same instance when getting data store")
  void getInventoryDataStore() {
    PersistenceFactory factory = new PersistenceFactoryImpl();
    ProductRepository items = factory.getInventoryDataStore();
    ProductRepository items1 = factory.getInventoryDataStore();
    assertSame(items, items1);
  }

  @Test
  @DisplayName("Providing the class returns the correct data store")
  void data_store() {
    PersistenceFactory factory = new PersistenceFactoryImpl();
    Repository<Category, CategoryId> c = factory.getRepository(Category.class);
    assertNotNull(c);
    assertTrue(c instanceof InMemoryCategoryRepository);
  }
}
