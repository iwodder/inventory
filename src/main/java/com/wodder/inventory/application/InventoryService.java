package com.wodder.inventory.application;

import com.wodder.inventory.dto.InventoryCountModel;
import com.wodder.inventory.dto.InventoryDto;
import com.wodder.inventory.dto.ReportDto;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface InventoryService {

  InventoryDto createInventory();

  Optional<InventoryDto> addInventoryCount(
      String inventoryId, String productId, double units, double cases);

  Optional<InventoryDto> addInventoryCounts(
      String inventoryId, Collection<InventoryCountModel> counts);

  void saveInventory(InventoryDto model);

  Optional<InventoryDto> loadInventory(String inventoryId);

  ReportDto generateInventoryReport(LocalDate start, LocalDate end);
}
