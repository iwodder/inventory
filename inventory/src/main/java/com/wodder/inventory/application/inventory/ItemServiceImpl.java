package com.wodder.inventory.application.inventory;

import com.wodder.inventory.domain.model.inventory.Item;
import com.wodder.inventory.domain.model.inventory.ItemId;
import com.wodder.inventory.domain.model.inventory.StorageLocation;
import com.wodder.inventory.dto.ItemDto;
import com.wodder.inventory.persistence.Repository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;


public class ItemServiceImpl implements ItemService {
  private final Repository<Item, ItemId> repository;
  private static final Function<Item, ItemDto> itemMapper = (item) ->
      ItemDto.builder()
      .withId(item.getId().getValue())
      .withProductId(item.getProductId())
      .withName(item.getName())
      .withLocation(item.getLocation().getName())
      .build();

  @Inject
  public ItemServiceImpl(@Named("InMemoryItemRepository") Repository<Item, ItemId> repository) {
    this.repository = repository;
  }

  @Override
  public String addItem(String productId, String name, String location, String measurementUnit) {
    Item newItem = Item.builder()
        .withName(name)
        .withProductId(productId)
        .withLocation(location)
        .withUnits(measurementUnit)
        .build();
    Item result = repository.createItem(newItem);

    return result.getId().getValue();
  }

  @Override
  public String addItem(CreateItemCommand command) {
    return addItem(
        command.getProductId(),
        command.getName(),
        command.getLocation(),
        command.getMeasurementUnit());
  }

  @Override
  public Optional<ItemDto> loadItem(String id) {
    Optional<Item> opt = repository.loadById(ItemId.of(id));

    return opt.map(itemMapper);
  }

  @Override
  public void deleteItem(String id) {
    repository.deleteItem(ItemId.of(id));
  }

  @Override
  public Optional<ItemDto> moveItem(String id, String location) {
    Optional<Item> opt = repository.loadById(ItemId.of(id));
    if (opt.isPresent()) {
      Item item = opt.get();
      item.updateLocation(StorageLocation.of(location));
      return repository.updateItem(item).map(itemMapper);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public String copyItemToNewLocation(String id, String location) {
    Optional<Item> opt = repository.loadById(ItemId.of(id));
    if (opt.isPresent()) {
      Item item = opt.get();
      Item dup =
          Item.builder()
              .withName(item.getName())
              .withProductId(item.getProductId())
              .withLocation(location)
              .withUnits(item.getUom().getUnit())
              .build();
      return repository.createItem(dup).getId().getValue();
    } else {
      return null;
    }
  }

  @Override
  public List<ItemDto> getAllItems() {
    return repository.loadAllItems()
        .stream()
        .map(itemMapper)
        .collect(Collectors.toList());
  }
}
