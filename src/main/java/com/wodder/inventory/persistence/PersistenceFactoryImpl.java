package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.Entity;
import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.product.Category;
import com.wodder.inventory.domain.model.product.Location;
import com.wodder.inventory.domain.model.product.Product;

public class PersistenceFactoryImpl implements PersistenceFactory {
  private final ProductRepository productRepository;
  private final InMemoryCategoryRepository categoryRepository;
  private final InMemoryLocationRepository locationRepository;
  private final InMemoryInventoryRepository inventoryRepository;

  public PersistenceFactoryImpl() {
    productRepository = new InMemoryProductRepository();
    categoryRepository = new InMemoryCategoryRepository();
    locationRepository = new InMemoryLocationRepository();
    inventoryRepository = new InMemoryInventoryRepository();
  }

  @Override
  public ProductRepository getInventoryDataStore() {
    return productRepository;
  }

  @Override
  public <T extends Entity<U>, U> Repository<T, U> getRepository(Class<T> clazz) {
    if (clazz.isAssignableFrom(Category.class)) {
      @SuppressWarnings("unchecked")
      Repository<T, U> c = (Repository<T, U>) categoryRepository;
      return c;
    } else if (clazz.isAssignableFrom(Location.class)) {
      @SuppressWarnings("unchecked")
      Repository<T, U> c = (Repository<T, U>) locationRepository;
      return c;
    } else if (clazz.isAssignableFrom(Inventory.class)) {
      @SuppressWarnings("unchecked")
      Repository<T, U> c = (Repository<T, U>) inventoryRepository;
      return c;
    } else if (clazz.isAssignableFrom(Product.class)) {
      @SuppressWarnings("unchecked")
      Repository<T, U> c = (Repository<T, U>) productRepository;
      return c;
    }
    return null;
  }
}
