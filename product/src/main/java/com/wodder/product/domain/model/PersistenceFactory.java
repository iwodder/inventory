package com.wodder.product.domain.model;


import com.wodder.product.domain.model.category.CategoryRepository;
import com.wodder.product.domain.model.product.Entity;
import com.wodder.product.domain.model.product.ProductRepository;
import com.wodder.product.domain.model.shipment.ShipmentRepository;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

public interface PersistenceFactory {

  @Produces
  @Default
  ProductRepository getProductRepository();

  @Produces
  @Default
  ShipmentRepository getShipmentRepository();

  @Produces
  @Default
  CategoryRepository getCategoryRepository();

  <T extends Entity<U>, U> Repository<T, U> getRepository(Class<T> clazz);
}
