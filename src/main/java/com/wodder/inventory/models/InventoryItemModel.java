package com.wodder.inventory.models;

import java.util.*;

public class InventoryItemModel {
	private Long id;
	private String name;
	private String category;
	private String location;
	private String units;
	private String itemPrice;
	private String casePrice;
	private boolean active;
	private int itemsPerCase;

	private InventoryItemModel(InventoryItemModelBuilder b) {
		this.id = b.id;
		this.name = b.name;
		this.category = b.category;
		this.location = b.location;
		this.active = b.active;
		this.units = b.units;
		this.itemsPerCase = b.itemsPerCase;
		this.itemPrice = b.itemPrice;
		this.casePrice = b.casePrice;
	}

	public InventoryItemModel(InventoryItemModel that) {
		this.id = that.id;
		this.name = that.name;
		this.category = that.category;
		this.active = that.active;
		this.location = that.location;
		this.units = that.units;
		this.itemsPerCase = that.itemsPerCase;
		this.itemPrice = that.itemPrice;
		this.casePrice = that.casePrice;
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

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public int getUnitsPerCase() {
		return itemsPerCase;
	}

	public void setUnitsPerCase(int itemsPerCase) {
		this.itemsPerCase = itemsPerCase;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getCasePrice() {
		return casePrice;
	}

	public void setCasePrice(String casePrice) {
		this.casePrice = casePrice;
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
		private String location;
		private String units;
		private String itemPrice;
		private String casePrice;
		private int itemsPerCase;
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

		public InventoryItemModelBuilder withLocation(String location) {
			this.location = location;
			return this;
		}

		public InventoryItemModelBuilder withUnitOfMeasurement(String uom) {
			this.units = uom;
			return this;
		}

		public InventoryItemModelBuilder withItemsPerCase(int itemsPerCase) {
			this.itemsPerCase = itemsPerCase;
			return this;
		}

		public InventoryItemModelBuilder withItemPrice(String itemPrice) {
			this.itemPrice = itemPrice;
			return this;
		}

		public InventoryItemModelBuilder withCasePrice(String casePrice) {
			this.casePrice = casePrice;
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
