package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.inventory.InventoryId;
import java.time.LocalDate;
import java.util.Optional;

public final class InMemoryInventoryRepository
    extends InMemoryRepository<Inventory, InventoryId> implements InventoryRepository {

  InMemoryInventoryRepository() {
  }

  @Override
  public Optional<Inventory> getInventoryByDate(LocalDate date) {
    return items.stream()
        .filter(inventory -> inventory.getInventoryDate().equals(date))
        .findAny();
  }
}
