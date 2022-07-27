package com.wodder.inventory.application.inventory;

import com.wodder.inventory.domain.model.inventory.Count;
import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.dto.CountDto;
import com.wodder.inventory.dto.InventoryDto;
import java.util.Collection;
import java.util.Optional;

public interface InventoryService {

  Inventory createInventory();

  Optional<Inventory> changeInventoryCount(
      String inventoryId, String itemId, String units, String cases);

  Optional<Inventory> updateInventoryCounts(
      String inventoryId, Collection<CountDto> counts);

  void saveInventory(InventoryDto model);

  Optional<Inventory> loadInventory(String inventoryId);

  Optional<Count> getCount(String inventoryId, String itemId);
}
