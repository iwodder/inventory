package com.wodder.inventory.domain;

import com.wodder.inventory.dtos.*;

public class InventoryItem {
	private final Long id;
	private final String name;
	private final String category;


	public InventoryItem(Long id, String name, String category) {
		this.id = id;
		this.name = name;
		this.category = category;
	}

	public InventoryItem(InventoryItemDto dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		this.category = dto.getCategory();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InventoryItem that = (InventoryItem) o;

		return getName().equals(that.getName());
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
