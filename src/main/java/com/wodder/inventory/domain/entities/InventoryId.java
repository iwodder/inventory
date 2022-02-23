package com.wodder.inventory.domain.entities;

import java.util.*;

public class InventoryId {
	
	private final String id;
	
	InventoryId() {
		id = UUID.randomUUID().toString();
	}

	InventoryId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public static InventoryId inventoryIdOf(String value) {
		return new InventoryId(value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof InventoryId)) return false;
		InventoryId that = (InventoryId) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
