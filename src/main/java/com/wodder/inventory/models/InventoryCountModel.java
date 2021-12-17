package com.wodder.inventory.models;

public class InventoryCountModel {

	private final Long itemId;
	private final String name;
	private final String category;
	private final String location;
	private int count;

	public InventoryCountModel(Long itemId, String name, String category, String location, int count) {
		this.itemId = itemId;
		this.name = name;
		this.category = category;
		this.location = location;
		this.count = count;
	}

	public InventoryCountModel(Long itemId, String name, String category, String location) {
		this(itemId, name, category, location, 0);
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

	public int getCount() {
		return count;
	}

	public String getLocation() {
		return location;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
