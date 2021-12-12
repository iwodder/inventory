package com.wodder.inventory.models;

import java.util.*;

public class InventoryItemModel {
	private Long id;
	private String name;
	private String category;
	private boolean active;

	private InventoryItemModel(InventoryItemModelBuilder b) {
		this.id = b.id;
		this.name = b.name;
		this.category = b.category;
		this.active = b.active;
	}

	public InventoryItemModel(InventoryItemModel that) {
		this.id = that.id;
		this.name = that.name;
		this.category = that.category;
		this.active = that.active;
	}

	private InventoryItemModel() {}

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public static InventoryItemModel fromMap(Map<String, String> values) {
		InventoryItemModel result = new InventoryItemModel();
		if (values != null && !values.isEmpty()) {
			if (values.get("ID") != null) {
				result.id = Long.parseLong(values.get("ID"));
			}
			result.name = values.get("NAME");
			result.category = values.get("CATEGORY");
		}
		return result;
	}

	public static InventoryItemModelBuilder builder() {
		return new InventoryItemModelBuilder();
	}

	public static class InventoryItemModelBuilder {
		private Long id;
		private String name;
		private String category;
		private boolean active;

		private InventoryItemModelBuilder() {
			//no-op
		}

		public InventoryItemModelBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public InventoryItemModelBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public InventoryItemModelBuilder withCategory(String category) {
			this.category = category;
			return this;
		}

		public InventoryItemModelBuilder isActive(boolean active) {
			this.active = active;
			return this;
		}

		public InventoryItemModel build() {
			return new InventoryItemModel(this);
		}
	}
}
