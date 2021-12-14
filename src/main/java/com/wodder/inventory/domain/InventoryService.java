package com.wodder.inventory.domain;

import com.wodder.inventory.models.*;

import java.time.*;
import java.util.*;

public interface InventoryService {
	InventoryModel createNewInventory();

	boolean saveInventory(InventoryModel i);

	Optional<InventoryModel> loadInventory(LocalDate date);
}
