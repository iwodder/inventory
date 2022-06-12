package com.wodder.inventory.application.implementations;

import com.wodder.inventory.application.InventoryService;
import com.wodder.inventory.application.ServiceFactory;
import com.wodder.inventory.domain.model.product.Category;
import com.wodder.inventory.domain.model.product.Location;
import com.wodder.inventory.domain.model.product.Product;
import com.wodder.inventory.persistence.PersistenceFactory;

public class ServiceFactoryImpl implements ServiceFactory {
  private PersistenceFactory factory;

  public ServiceFactoryImpl() {
  }

  public ServiceFactoryImpl(PersistenceFactory factory) {
    this.factory = factory;
  }

  @Override
  public <T> T getService(Class<T> service) {
    try {
      if (service.isAssignableFrom(ProductServiceImpl.class)) {
        return service.cast(
            new ProductServiceImpl(
                factory.getRepository(Product.class),
                factory.getRepository(Category.class),
                factory.getRepository(Location.class)));
      } else if (service.isAssignableFrom(CategoryServiceImpl.class)) {
        return service.cast(new CategoryServiceImpl(factory.getRepository(Category.class)));
      } else if (service.isAssignableFrom(LocationServiceImpl.class)) {
        return service.cast(new LocationServiceImpl(factory.getRepository(Location.class)));
      } else if (service.isAssignableFrom(InventoryService.class)) {
        return service.cast(
            new InventoryServiceImpl(
                factory.getInventoryRepository(), factory.getRepository(Product.class)));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
