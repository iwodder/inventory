package com.wodder.inventory.domain;

import com.wodder.inventory.dtos.*;

public class InventoryItem {
	private Long id;
	private String name;
	private  String category;
	private int count;
	private boolean active;

	public InventoryItem(Long id, String name, String category) {
		this.id = id;
		this.name = name;
		this.category = category;
	}

	public InventoryItem(InventoryItem that) {
		this.id = that.id;
		this.name = that.name;
		this.category = that.category;
		this.count = that.count;
		this.active = that.active;
	}

	public InventoryItem(InventoryItemDto dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		this.category = dto.getCategory();
		this.active = dto.isActive();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCount(int count) {
		if (count < 0) {
			throw new IllegalArgumentException("Inventory item cannot have negative count");
		}
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public InventoryItemDto toDto() {
		return InventoryItemDto.builder()
				.withId(this.id)
				.withName(this.name)
				.withCategory(this.category)
				.isActive(this.active)
				.build();
	}
}
