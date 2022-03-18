package com.wodder.inventory.application;

import com.wodder.inventory.models.*;

import java.util.*;

public interface InventoryService {

	InventoryModel createInventory();

	Optional<InventoryModel> addInventoryCount(String inventoryId, String productId, double units, double cases);

	Optional<InventoryModel> addInventoryCounts(String inventoryId, Collection<InventoryCountModel> counts);
}
