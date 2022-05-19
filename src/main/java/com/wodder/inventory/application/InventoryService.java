package com.wodder.inventory.application;

import com.wodder.inventory.dto.*;

import java.util.*;

public interface InventoryService {

	InventoryDto createInventory();

	Optional<InventoryDto> addInventoryCount(String inventoryId, String productId, double units, double cases);

	Optional<InventoryDto> addInventoryCounts(String inventoryId, Collection<InventoryCountModel> counts);

	void saveInventory(InventoryDto model);

	Optional<InventoryDto> loadInventory(String inventoryId);
}
