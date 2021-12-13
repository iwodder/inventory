package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;

public class InventoryCount {

	private final long itemId;
	private final String name;
	private final String category;
	private final int count;

	public InventoryCount(Long itemId, String name, String category, Integer count) {
		this.itemId = itemId;
		this.name = name;
		this.category = category;
		this.count = count;
	}

	public InventoryCount(Long itemId, String name, String category) {
		this(itemId, name, category, 0);
	}

	public InventoryCount(InventoryCount that) {
		this.itemId = that.itemId;
		this.name = that.name;
		this.category = that.category;
		this.count = that.count;
	}

	public InventoryCount(InventoryCountModel model) {
		this.itemId = model.getItemId();
		this.name = model.getName();
		this.category = model.getCategory();
		this.count = model.getCount();
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
		return new InventoryCountModel(itemId, name, category, count);
	}
}
