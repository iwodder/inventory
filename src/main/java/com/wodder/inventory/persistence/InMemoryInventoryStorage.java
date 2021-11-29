package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.*;

import java.time.*;
import java.util.*;

public class InMemoryInventoryStorage implements InventoryStorage {
	private final Map<LocalDate, Inventory> db;

	InMemoryInventoryStorage() {
		db = new HashMap<>();
	}

	@Override
	public boolean save(Inventory i) {
		if (db.containsKey(i.date())) {
			return false;
		} else {
			db.put(i.date(), new Inventory(i));
		}
		return true;
	}

	@Override
	public Optional<Inventory> load(Inventory i) {
		if (db.containsKey(i.date())) {
			return Optional.of(new Inventory(db.get(i.date())));
		} else {
			return Optional.empty();
		}
	}
}
