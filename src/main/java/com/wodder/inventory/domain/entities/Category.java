package com.wodder.inventory.domain.entities;

import java.util.*;

public class Category {
	private static final String DEFAULT_NAME = "unassigned";
	private String name;

	public Category() {
		this(DEFAULT_NAME);
	}

	public Category(String name) {
		setName(name);
	}

	public Category(Category that) {
		this(that.getName());
	}

	public static Category defaultCategory() {
		return new Category();
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		if (name != null && name.length() > 0) {
			this.name = name;
		} else {
			this.name = DEFAULT_NAME;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Category category = (Category) o;
		return getName().equals(category.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
