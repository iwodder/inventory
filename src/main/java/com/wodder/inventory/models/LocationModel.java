package com.wodder.inventory.models;

import com.wodder.inventory.domain.entities.*;

public class LocationModel {
	private String name;
	private Long id;

	public LocationModel(Location location) {
		this.name = location.getName();
		this.id = location.getId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LocationModel{" +
				"name='" + name + '\'' +
				", id=" + id +
				'}';
	}
}
