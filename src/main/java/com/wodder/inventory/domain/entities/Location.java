package com.wodder.inventory.domain.entities;

public class Location extends Entity {
	private static final String DEFAULT_VALUE = "unassigned";
	private String name;

	public Location() {
		this(DEFAULT_VALUE);
	}

	public Location(String name) {
		this(0L, name);
	}

	public Location(Location that) {
		this(that.getId(), that.getName());
	}

	public Location(Long id, String name) {
		super(id);
		setName(name);
	}

	public String getName() {
		return name;
	}

	public static Location defaultLocation() {
		return new Location();
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

	@Override
	public String toString() {
		return name;
	}
}
