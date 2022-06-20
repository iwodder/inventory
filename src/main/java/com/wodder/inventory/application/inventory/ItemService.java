package com.wodder.inventory.application.inventory;

import com.wodder.inventory.dto.ItemDto;
import java.util.Optional;

public interface ItemService {

  String addItem(String productId, String name, String location, String measurementUnit);

  Optional<ItemDto> loadItem(String id);

  void deleteItem(String id);

  void moveItem(String id, String location);

  String duplicateItem(String id, String location);
}
