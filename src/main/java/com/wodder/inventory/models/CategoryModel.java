package com.wodder.inventory.models;

import com.wodder.inventory.domain.entities.*;

public class CategoryModel {
	private String name;
	private String id;

	public CategoryModel() {

	}

	public CategoryModel(Category category) {
		this.name = category.getName();
		this.id = category.getId().getId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CategoryModel{" +
				"name='" + name + '\'' +
				", id=" + id +
				'}';
	}
}
