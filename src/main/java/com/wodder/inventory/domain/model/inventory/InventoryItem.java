package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.domain.model.product.*;
import com.wodder.inventory.dto.*;

import java.util.*;

public class InventoryItem {

	private final String name;
	private final String location;
	private final String category;
	private final UnitOfMeasurement uom;
	private final Price price;
	private final InventoryCount count;

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

	public InventoryItem updateCount(InventoryCount count) {
		return new InventoryItem(this.name, this.location, this.category, this.uom, this.price, count);
	}

	public static InventoryItem fromProduct(Product p) {
		return new InventoryItem(p.getName(), p.getLocation(), p.getCategory(), p.getUnits(), p.getPrice(), InventoryCount.countOfZero());
	}

	public static InventoryItem fromModel(InventoryItemDto model) {
		return new InventoryItem(model.getName(), model.getLocation(), model.getCategory(),
				new UnitOfMeasurement(model.getUnits(), Integer.parseInt(model.getItemsPerCase())),
				new Price(model.getUnitPrice(), model.getCasePrice()),
				new InventoryCount(Double.parseDouble(model.getNumberOfUnits()), Double.parseDouble(model.getNumberOfCases())));
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

	public InventoryItemDto toModel() {
		return new InventoryItemDto(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof InventoryItem)) return false;
		InventoryItem item = (InventoryItem) o;
		return getName().equals(item.getName()) &&
				getLocation().equals(item.getLocation()) &&
				getCategory().equals(item.getCategory());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getLocation(), getCategory());
	}
}
