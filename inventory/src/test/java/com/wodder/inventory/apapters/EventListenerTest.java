package com.wodder.inventory.apapters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wodder.inventory.application.inventory.CreateItemCommand;
import com.wodder.inventory.application.inventory.ItemService;
import com.wodder.inventory.dto.ItemDto;
import com.wodder.product.domain.model.product.ProductCreated;
import com.wodder.product.domain.model.product.ProductId;
import com.wodder.product.domain.model.product.ProductName;
import com.wodder.product.domain.model.product.UnitOfMeasurement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventListenerTest {
  private FakeItemService service;

  @BeforeEach
  void setup() {
    service = new FakeItemService();
  }

  @Test
  @DisplayName("Should listen for a product created event")
  void product_created() {
    EventListener listener = new EventListener(service);
    listener.productCreated(
        new ProductCreated(
            ProductId.productIdOf("p123"),
            ProductName.of("Milk"),
            UnitOfMeasurement.of("Gallons")));

    assertEquals(1, service.createItemCommands.size());
    CreateItemCommand cmd = service.createItemCommands.get(0);

    assertEquals("p123", cmd.getProductId());
    assertEquals("Milk", cmd.getName());
    assertEquals("Gallons", cmd.getMeasurementUnit());
  }

  @Test
  @DisplayName("Items created on event are unassigned")
  void unassigned_location() {
    EventListener listener = new EventListener(service);
    listener.productCreated(
        new ProductCreated(
            ProductId.productIdOf("p123"),
            ProductName.of("Milk"),
            UnitOfMeasurement.of("Gallons")));

    assertEquals(1, service.createItemCommands.size());
    CreateItemCommand cmd = service.createItemCommands.get(0);

    assertEquals("UNASSIGNED", cmd.getLocation());
  }

  static class FakeItemService implements ItemService {
    List<CreateItemCommand> createItemCommands = new ArrayList<>();

    @Override
    public String addItem(String productId, String name, String location, String measurementUnit) {
      return null;
    }

    @Override
    public String addItem(CreateItemCommand command) {
      createItemCommands.add(command);
      return "item123";
    }

    @Override
    public Optional<ItemDto> loadItem(String id) {
      return Optional.empty();
    }

    @Override
    public void deleteItem(String id) {

    }

    @Override
    public Optional<ItemDto> moveItem(String id, String location) {
      return Optional.empty();
    }

    @Override
    public String copyItemToNewLocation(String id, String location) {
      return null;
    }
  }
}
