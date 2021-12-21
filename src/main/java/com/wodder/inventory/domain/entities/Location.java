package com.wodder.inventory.domain.entities;

public class Location {
	private static final String DEFAULT_VALUE = "unassigned";
	private String name;

	public Location(Location that) {
		this(that.getName());
	}

	public Location(String name) {
		setName(name);
	}

	Location() {
		this(DEFAULT_VALUE);
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Location) {
			return this.name.equals(((Location) obj).name);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	private void setName(String name) {
		if (name != null && name.length() > 0) {
			this.name = name;
		} else {
			this.name = DEFAULT_VALUE;
		}
	}
}
