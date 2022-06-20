package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.inventory.Item;
import com.wodder.inventory.domain.model.inventory.ItemId;

public class InMemoryItemRepository extends InMemoryRepository<Item, ItemId> {
  InMemoryItemRepository() {
  }
}
