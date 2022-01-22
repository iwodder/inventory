package com.wodder.inventory.domain.entities;

public abstract class Entity {
	protected final Long id;

	Entity(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
