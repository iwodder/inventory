package com.wodder.inventory.models;

import java.io.*;
import java.util.*;

public class ProductModel implements Serializable {
	private String id;
	private String name;
	private String category;
	private String location;
	private String units;
	private String itemPrice;
	private String casePrice;
	private boolean active;
	private int unitsPerCase;

	private ProductModel(ProductModelBuilder b) {
		this.id = b.id;
		this.name = b.name;
		this.category = b.category;
		this.location = b.location;
		this.active = b.active;
		this.units = b.units;
		this.unitsPerCase = b.itemsPerCase;
		this.itemPrice = b.itemPrice;
		this.casePrice = b.casePrice;
	}

	public ProductModel(ProductModel that) {
		this.id = that.id;
		this.name = that.name;
		this.category = that.category;
		this.active = that.active;
		this.location = that.location;
		this.units = that.units;
		this.unitsPerCase = that.unitsPerCase;
		this.itemPrice = that.itemPrice;
		this.casePrice = that.casePrice;
	}

	public ProductModel() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
		return unitsPerCase;
	}

	public void setUnitsPerCase(int itemsPerCase) {
		this.unitsPerCase = itemsPerCase;
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

	public static ProductModel fromMap(Map<String, String> values) {
		ProductModel result = new ProductModel();
		if (values != null && !values.isEmpty()) {
			result.id = values.get("ID");
			result.name = values.get("NAME");
			result.category = values.get("CATEGORY");
		}
		return result;
	}

	public static ProductModelBuilder builder() {
		return new ProductModelBuilder();
	}

	public static class ProductModelBuilder implements Serializable {
		private String id;
		private String name;
		private String category;
		private String location;
		private String units;
		private String itemPrice;
		private String casePrice;
		private int itemsPerCase;
		private boolean active;

		private ProductModelBuilder() {
			//no-op
		}

		public ProductModelBuilder withId(String id) {
			this.id = id;
			return this;
		}

		public ProductModelBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public ProductModelBuilder withCategory(String category) {
			this.category = category;
			return this;
		}

		public ProductModelBuilder withLocation(String location) {
			this.location = location;
			return this;
		}

		public ProductModelBuilder withUnitOfMeasurement(String uom) {
			this.units = uom;
			return this;
		}

		public ProductModelBuilder withItemsPerCase(int itemsPerCase) {
			this.itemsPerCase = itemsPerCase;
			return this;
		}

		public ProductModelBuilder withItemPrice(String itemPrice) {
			this.itemPrice = itemPrice;
			return this;
		}

		public ProductModelBuilder withCasePrice(String casePrice) {
			this.casePrice = casePrice;
			return this;
		}

		public ProductModelBuilder isActive(boolean active) {
			this.active = active;
			return this;
		}

		public ProductModel build() {
			return new ProductModel(this);
		}
	}
}
