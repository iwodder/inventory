package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;

public class InventoryItem {
	private Long id;
	private String name;
	private  String category;
	private int onHandQty;
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
		this.onHandQty = that.onHandQty;
		this.active = that.active;
	}

	public InventoryItem(InventoryItemModel model) {
		this.id = model.getId();
		this.name = model.getName();
		this.category = model.getCategory();
		this.active = model.isActive();
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

	public void setOnHandQty(int onHandQty) {
		if (onHandQty < 0) {
			throw new IllegalArgumentException("Inventory item cannot have negative count");
		}
		this.onHandQty = onHandQty;
	}

	public int getOnHandQty() {
		return onHandQty;
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

	public InventoryItemModel toItemModel() {
		return InventoryItemModel.builder()
				.withId(this.id)
				.withName(this.name)
				.withCategory(this.category)
				.isActive(this.active)
				.build();
	}

	public InventoryCountModel toCountModel() {
		return new InventoryCountModel(id, name, category, onHandQty);
	}
}
