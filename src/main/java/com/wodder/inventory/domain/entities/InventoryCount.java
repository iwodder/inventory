package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;

public class InventoryCount {

	private final Long itemId;
	private final String name;
	private final String category;
	private final String location;
	private int count;

	public InventoryCount(Long itemId, String name, String category, String location, Integer count) {
		this.itemId = itemId;
		this.name = name;
		this.category = category;
		this.location = location;
		setCount(count);
	}

	public InventoryCount(Long itemId, String name, String category, String location) {
		this(itemId, name, category, location, 0);
	}

	public InventoryCount(InventoryCount that) {
		this(that.getItemId(), that.getName(), that.getCategory(), that.getLocation(), that.getCount());
	}

	public InventoryCount(InventoryCountModel model) {
		this(model.getItemId(), model.getName(), model.getCategory(), model.getLocation(), model.getCount());
	}

	public Long getItemId() {
		return itemId;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getLocation() {
		return location;
	}

	public int getCount() {
		return count;
	}

	private void setCount(int count) {
		if (count < 0) {
			throw new IllegalArgumentException();
		}
		this.count = count;
	}

	public InventoryCountModel toModel() {
		return new InventoryCountModel(itemId, name, category, location,count);
	}
}
