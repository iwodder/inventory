package com.wodder.product.persistence;

import com.wodder.product.domain.model.category.Category;
import com.wodder.product.domain.model.product.Entity;
import com.wodder.product.domain.model.product.Product;

public class PersistenceFactoryImpl implements PersistenceFactory {
  private final ProductRepository productRepository;
  private final InMemoryCategoryRepository categoryRepository;
  private final InMemoryShipmentRepository shipmentRepository;

  public PersistenceFactoryImpl() {
    productRepository = new InMemoryProductRepository();
    categoryRepository = new InMemoryCategoryRepository();
    shipmentRepository = new InMemoryShipmentRepository();
  }

  @Override
  public ProductRepository getProductRepository() {
    return productRepository;
  }

  @Override
  public ShipmentRepository getShipmentRepository() {
    return shipmentRepository;
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
