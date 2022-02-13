package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

public final class InMemoryLocationRepository extends InMemoryRepository<Location> {

	InMemoryLocationRepository() {};

	@Override
	public Location createItem(Location item) {
		Location l = new Location(getNextId(), item.getName());
		addItem(l);
		return l;
	}
}
