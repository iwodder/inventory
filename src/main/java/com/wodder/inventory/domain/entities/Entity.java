package com.wodder.inventory.domain.entities;

public abstract class Entity {
	protected Long id;

	Entity(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
