package com.wodder.inventory.domain.entities;

import java.util.*;

public class Category {
	private static final String DEFAULT_NAME = "unassigned";
	private final Long id;
	private String name;

	public Category() {
		this(0L, DEFAULT_NAME);
	}

	public Category(String name) {
		this(0L, name);
	}

	public Category(Category that) {
		this(that.getId(), that.getName());
	}

	public Category(Long id, String name) {
		this.id = id;
		setName(name);
	}

	public static Category defaultCategory() {
		return new Category();
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
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

	@Override
	public String toString() {
		return name;
	}
}
