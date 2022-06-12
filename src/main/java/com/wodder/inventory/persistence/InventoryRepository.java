package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.inventory.InventoryId;
import java.time.LocalDate;
import java.util.Optional;

public interface InventoryRepository extends Repository<Inventory, InventoryId> {

  Optional<Inventory> getInventoryByDate(LocalDate date);
}
