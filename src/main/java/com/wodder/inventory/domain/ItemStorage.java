package com.wodder.inventory.domain;

import com.wodder.inventory.dtos.*;

import java.util.*;

public interface ItemStorage {

	Optional<InventoryItemDto> addItem(InventoryItemDto newItem);

	Boolean deleteItem(InventoryItemDto itemToDelete);

	Optional<InventoryItemDto> updateItem(InventoryItemDto updatedItem);

	Optional<InventoryItemDto> readItem(Long itemId);

	List<InventoryItemDto> readAllItems();
}
