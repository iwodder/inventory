package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.inventory.InventoryId;

public final class InMemoryInventoryRepository extends InMemoryRepository<Inventory, InventoryId> {

  InMemoryInventoryRepository() {
  }
}
