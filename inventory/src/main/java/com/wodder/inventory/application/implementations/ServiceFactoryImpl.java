package com.wodder.inventory.application.implementations;

import com.wodder.inventory.application.ServiceFactory;
import com.wodder.inventory.application.inventory.InventoryService;
import com.wodder.inventory.application.inventory.InventoryServiceImpl;
import com.wodder.inventory.application.inventory.ItemService;
import com.wodder.inventory.application.inventory.ItemServiceImpl;
import com.wodder.inventory.domain.model.inventory.Item;
import com.wodder.inventory.persistence.PersistenceFactory;
import javax.inject.Inject;

public class ServiceFactoryImpl implements ServiceFactory {
  private PersistenceFactory factory;

  public ServiceFactoryImpl() {
  }

  @Inject
  public ServiceFactoryImpl(PersistenceFactory factory) {
    this.factory = factory;
  }

  @Override
  public <T> T getService(Class<T> service) {
    try {
      if (service.isAssignableFrom(InventoryService.class)) {
        return service.cast(
            new InventoryServiceImpl(
                factory.getInventoryRepository(),
                factory.getRepository(Item.class)
            )
        );
      } else if (service.isAssignableFrom(ItemService.class)) {
        return service.cast(
            new ItemServiceImpl(
                factory.getRepository(Item.class)
            )
        );
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
