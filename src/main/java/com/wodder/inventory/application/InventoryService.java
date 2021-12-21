package com.wodder.inventory.application;

import com.wodder.inventory.models.*;

import java.time.*;
import java.util.*;

public interface InventoryService {
	InventoryModel createNewInventory();

	boolean saveInventory(InventoryModel i);

	Optional<InventoryModel> loadInventory(LocalDate date);
}