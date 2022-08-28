package com.wodder.product.domain.model;


import com.wodder.product.domain.model.product.Entity;
import com.wodder.product.domain.model.product.ProductRepository;
import com.wodder.product.domain.model.shipment.ShipmentRepository;

public interface PersistenceFactory {

  ProductRepository getProductRepository();

  ShipmentRepository getShipmentRepository();

  <T extends Entity<U>, U> Repository<T, U> getRepository(Class<T> clazz);
}
