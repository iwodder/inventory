package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

public class InMemoryLocationRepository extends InMemoryRepository<Location> {

	@Override
	public Location createItem(Location item) {
		Location l = new Location(getNextId(), item.getName());
		addItem(l);
		return l;
	}
}
