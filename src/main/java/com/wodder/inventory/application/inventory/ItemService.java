package com.wodder.inventory.application.inventory;

import com.wodder.inventory.dto.ItemDto;
import java.util.Optional;

public interface ItemService {

  String addItem(String productId, String name, String location, String measurementUnit);

  String addItem(CreateItemCommand command);

  Optional<ItemDto> loadItem(String id);

  void deleteItem(String id);

  Optional<ItemDto> moveItem(String id, String location);

  String copyItemToNewLocation(String id, String location);
}
