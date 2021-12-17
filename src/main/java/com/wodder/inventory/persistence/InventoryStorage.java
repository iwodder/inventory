package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.util.*;

public interface InventoryStorage {

	boolean save(Inventory i);

	Optional<Inventory> load(Inventory i);
}