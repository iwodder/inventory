package com.wodder.inventory.domain.entities;

import java.util.*;

public class InventoryItem {

	private Integer id;
	private final String name;
	private final String location;
	private final String category;
	private final UnitOfMeasurement uom;
	private final Price price;
	private InventoryCount count;

	InventoryItem(String name, String location, String category, UnitOfMeasurement uom, Price price, InventoryCount count) {
		this.name = name;
		this.location = location;
		this.category = category;
		this.uom = uom;
		this.price = price;
		this.count = count;
	}

	InventoryItem(String name, String location, String category, UnitOfMeasurement uom, Price price) {
		this(name, location, category, uom, price, null);
	}

	InventoryItem(InventoryItem that) {
		this(that.name, that.location, that.category, new UnitOfMeasurement(that.uom), new Price(that.price), new InventoryCount(that.count));
	}

	InventoryItem updateCount(InventoryCount count) {
		return new InventoryItem(this.name, this.location, this.category, this.uom, this.price, count);
	}

	public static InventoryItem fromProduct(Product p) {
		return new InventoryItem(p.getName(), p.getLocation(), p.getCategory(), p.getUnits(), p.getPrice(), InventoryCount.countOfZero());
	}

	public void setId(int id) {
		if (this.id == null) {
			this.id = id;
		} else {
			throw new IllegalArgumentException("Cannot set id twice");
		}
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public String getCategory() {
		return category;
	}

	public UnitOfMeasurement getUom() {
		return uom;
	}

	public Price getPrice() {
		return price;
	}

	public InventoryCount getCount() {
		return count;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof InventoryItem)) return false;
		InventoryItem item = (InventoryItem) o;
		return getName().equals(item.getName()) && getLocation().equals(item.getLocation()) && getCategory().equals(item.getCategory());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getLocation(), getCategory());
	}
}
