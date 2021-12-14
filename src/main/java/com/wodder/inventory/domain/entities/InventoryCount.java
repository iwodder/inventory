package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;

public class InventoryCount {

	private final long itemId;
	private final String name;
	private final String category;
	private final String location;
	private final int count;

	public InventoryCount(Long itemId, String name, String category, String location, Integer count) {
		this.itemId = itemId;
		this.name = name;
		this.category = category;
		this.location = location;
		this.count = count;
	}

	public InventoryCount(Long itemId, String name, String category, String location) {
		this(itemId, name, category, location, 0);
	}

	public InventoryCount(InventoryCount that) {
		this.itemId = that.itemId;
		this.name = that.name;
		this.category = that.category;
		this.location = that.location;
		this.count = that.count;
	}

	public InventoryCount(InventoryCountModel model) {
		this.itemId = model.getItemId();
		this.name = model.getName();
		this.category = model.getCategory();
		this.count = model.getCount();
		this.location = model.getLocation();
	}

	public long getItemId() {
		return itemId;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public int getCount() {
		return count;
	}

	public InventoryCountModel toModel() {
		return new InventoryCountModel(itemId, name, category, location,count);
	}
}
