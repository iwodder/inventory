package com.wodder.inventory.application.inventory;

import com.wodder.inventory.domain.model.inventory.Item;
import com.wodder.inventory.domain.model.inventory.ItemId;
import com.wodder.inventory.domain.model.inventory.StorageLocation;
import com.wodder.inventory.dto.ItemDto;
import com.wodder.inventory.persistence.Repository;
import java.util.Optional;


public class ItemServiceImpl implements ItemService {
  private final Repository<Item, ItemId> repository;

  public ItemServiceImpl(Repository<Item, ItemId> repository) {
    this.repository = repository;
  }

  @Override
  public String addItem(String productId, String name, String location, String measurementUnit) {
    Item newItem = Item.builder()
        .withName(name)
        .withLocation(location)
        .withUnits(measurementUnit)
        .build();
    Item result = repository.createItem(newItem);

    return result.getId().getValue();
  }

  @Override
  public Optional<ItemDto> loadItem(String id) {
    Optional<Item> opt = repository.loadById(ItemId.of(id));

    return opt.map((item) ->
        ItemDto.builder()
            .withId(item.getId().getValue())
            .withName(item.getName())
            .withLocation(item.getLocation().getName())
            .build());
  }

  @Override
  public void deleteItem(String id) {
    repository.deleteItem(ItemId.of(id));
  }

  @Override
  public void moveItem(String id, String location) {
    Optional<Item> opt = repository.loadById(ItemId.of(id));
    if (opt.isPresent()) {
      Item item = opt.get();
      item.updateLocation(StorageLocation.of(location));
      repository.updateItem(item);
    }
  }

  @Override
  public String duplicateItem(String id, String location) {
    Optional<Item> opt = repository.loadById(ItemId.of(id));
    if (opt.isPresent()) {
      Item item = opt.get();
      Item dup =
          Item.builder()
              .withName(item.getName())
              .withLocation(location)
              .withUnits(item.getUom().getUnit())
              .build();
      return repository.createItem(dup).getId().getValue();
    } else {
      return null;
    }
  }
}
