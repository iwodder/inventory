package com.wodder.inventory.apapters;

import com.wodder.inventory.application.inventory.CreateItemCommand;
import com.wodder.inventory.application.inventory.ItemService;
import com.wodder.inventory.domain.model.inventory.StorageLocation;
import com.wodder.product.domain.model.product.ProductCreated;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventListener.class);
  private final ItemService service;

  @Inject
  EventListener(ItemService service) {
    this.service = service;
  }

  public void productCreated(@Observes ProductCreated event) {
    LOGGER.info(">> productCreated(event={})", event);
    CreateItemCommand cmd = new CreateItemCommand();
    cmd.setProductId(event.getProductId().getValue());
    cmd.setName(event.getName().getValue());
    cmd.setMeasurementUnit(event.getUnitOfMeasurement().getUnit());
    cmd.setLocation(StorageLocation.unassigned().getName());
    String itemId = service.addItem(cmd);
    LOGGER.info("<< Successfully created item, id={}", itemId);
  }
}
