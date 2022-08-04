package com.wodder.product.persistence;


import com.wodder.product.domain.model.product.Entity;

public interface PersistenceFactory {

  ProductRepository getProductRepository();

  ShipmentRepository getShipmentRepository();

  <T extends Entity<U>, U> Repository<T, U> getRepository(Class<T> clazz);
}
