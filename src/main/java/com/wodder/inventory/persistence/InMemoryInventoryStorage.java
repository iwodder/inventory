package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.time.*;
import java.util.*;

public final class InMemoryInventoryStorage implements InventoryStorage {
	private final Map<LocalDate, Inventory> db;

	InMemoryInventoryStorage() {
		db = new HashMap<>();
	}

	@Override
	public boolean save(Inventory i) {
		if (isPresent(i)) {
			return false;
		} else {
			db.put(i.date(), new Inventory(i));
			return true;
		}
	}

	@Override
	public Optional<Inventory> load(Inventory i) {
		if (isPresent(i)) {
			return Optional.of(new Inventory(db.get(i.date())));
		} else {
			return Optional.empty();
		}
	}

	private boolean isPresent(Inventory i) {
		return db.containsKey(i.date());
	}
}
