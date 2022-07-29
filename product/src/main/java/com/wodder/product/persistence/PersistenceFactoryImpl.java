package com.wodder.product.persistence;

import com.wodder.product.domain.model.product.Category;
import com.wodder.product.domain.model.product.Entity;
import com.wodder.product.domain.model.product.Product;

public class PersistenceFactoryImpl implements PersistenceFactory {
  private final ProductRepository productRepository;
  private final InMemoryCategoryRepository categoryRepository;

  public PersistenceFactoryImpl() {
    productRepository = new InMemoryProductRepository();
    categoryRepository = new InMemoryCategoryRepository();
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
    } else if (clazz.isAssignableFrom(Product.class)) {
      @SuppressWarnings("unchecked")
      Repository<T, U> c = (Repository<T, U>) productRepository;
      return c;
    }
    return null;
  }
}
