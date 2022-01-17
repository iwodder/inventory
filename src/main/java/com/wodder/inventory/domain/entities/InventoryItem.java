package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;

import java.math.*;

public class InventoryItem {
	private Long id;
	private String name;
	private Category category;
	private Location location;
	private UnitOfMeasurement uom;
	private Price price;
	private boolean active;

	public InventoryItem(String name, Category category, Location location) {
		this(null, name, category, location, true, null, null);
	}

	public InventoryItem(String name, Category category, Location location, UnitOfMeasurement unitOfMeasurement, Price price) {
		this(null, name, category, location, true, unitOfMeasurement, price);
	}

	public InventoryItem(Long id, String name, Category category, Location location) {
		this(id, name, category, location, true, null, null);
	}

	public InventoryItem(Long id, String name, Category category, Location location, Boolean active) {
		this(id, name, category, location, active, null, null);
	}
	
	public InventoryItem(Long id, String name, Category category, Location location, UnitOfMeasurement uom) {
		this(id, name, category, location, true, uom, null);
	}

	public InventoryItem(Long id, String name, Category category, Location location, UnitOfMeasurement uom, Price price) {
		this(id, name, category, location, true, uom, price);
	}

	public InventoryItem(InventoryItem that) {
		this.id = that.id;
		this.name = that.name;
		this.category = that.category;
		this.active = that.active;
		this.location = that.location;
		this.uom = that.uom;
	}

	public InventoryItem(InventoryItemModel model) {
		this.id = model.getId();
		this.name = model.getName();
		this.category = model.getCategory() != null ? new Category(model.getCategory()) : new Category();
		this.active = model.isActive();
		this.location = model.getLocation() != null ? new Location(model.getName()) : new Location();
	}

	private InventoryItem(Long id, String name, Category c, Location l, Boolean a, UnitOfMeasurement u, Price p) {
		this.id = id;
		setName(name);
		setCategory(c);
		this.location = l;
		this.active = a;
		this.uom = u;
		this.price = p;
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

	private void setCategory(Category c) {
		if (c == null) {
			throw new IllegalArgumentException("Category is required");
		}
		this.category = c;
	}

	public void updateCategory(Category category) {
		if (this.category.equals(category)) {
			throw new IllegalArgumentException("Cannot update to the same category");
		}
		setCategory(category);
	}

	public void updateLocation(Location location) {
		if (this.location.equals(location)) {
			throw new IllegalArgumentException("Cannot update to the same location");
		}
		setLocation(location);
	}

	public void updateName(String name) {
		if (this.name.equals(name)) {
			throw new IllegalArgumentException("Cannot update to the same name");
		}
		setName(name);
	}

	public void updateUnitOfMeasurement(UnitOfMeasurement uom) {
		if (this.uom.equals(uom)) {
			throw new IllegalArgumentException("Cannot update Unit of Measurement to the same value");
		}
		this.uom = uom;
	}

	public void updatePrice(Price price) {
		this.price = price;
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

	public UnitOfMeasurement getUnits() {
		return uom;
	}

	public int getUnitsPerCase() {
		return uom.getItemsPerCase();
	}

	public BigDecimal getUnitPrice() {
		return price.getUnitPrice();
	}

	public BigDecimal getCasePrice() {
		return price.getCasePrice();
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
		InventoryItemModel.InventoryItemModelBuilder b = InventoryItemModel.builder()
				.withId(this.id)
				.withName(this.name)
				.withCategory(this.category.getName())
				.withLocation(this.location.getName())
				.isActive(this.active);

		if (this.uom != null) {
			b.withUnitOfMeasurement(uom.getUnit()).withItemsPerCase(uom.getItemsPerCase());
		}
		if (this.price != null) {
			b.withItemPrice(price.getUnitPrice().toString()).withCasePrice(price.getCasePrice().toString());
		}
		return b.build();
	}
}
