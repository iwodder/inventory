package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;

public class InventoryItem {
	private Long id;
	private String name;
	private Category category;
	private Location location;
	private boolean active;

	public InventoryItem(String name, Category category, Location location) {
		this(null, name, category, location, true);
	}

	public InventoryItem(Long id, String name, Category category, Location location) {
		this(id, name, category, location, true);
	}

	public InventoryItem(InventoryItem that) {
		this.id = that.id;
		this.name = that.name;
		this.category = that.category;
		this.active = that.active;
		this.location = that.location;
	}

	public InventoryItem(InventoryItemModel model) {
		this.id = model.getId();
		this.name = model.getName();
		this.category = model.getCategory() != null ? new Category(model.getCategory()) : new Category();
		this.active = model.isActive();
		this.location = model.getLocation() != null ? new Location(model.getName()) : new Location();
	}

	public InventoryItem(Long id, String name, Category c, Location l, Boolean a) {
		this.id = id;
		setName(name);
		this.category = c;
		this.location = l;
		this.active = a;
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
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException(name == null ? "Name was null" : "Name was blank");
		}
		this.name = name;
	}

	public String getCategory() {
		return category.getName();
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLocation() {
		return location.getName();
	}

	public void setLocation(Location location) {
		this.location = location;
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
				.withCategory(this.category.getName())
				.withLocation(this.location.getName())
				.isActive(this.active)
				.build();
	}
}
