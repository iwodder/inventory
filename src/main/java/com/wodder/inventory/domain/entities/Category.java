package com.wodder.inventory.domain.entities;

public class Category {
	private static final String DEFAULT_NAME = "unassigned";
	private String name;

	public Category() {
		this(DEFAULT_NAME);
	}

	public Category(String name) {
		setName(name);
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
}
