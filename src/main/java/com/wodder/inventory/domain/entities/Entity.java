package com.wodder.inventory.domain.entities;

public abstract class Entity<T> {
	protected Long databaseId;
	protected T id;

	Entity(T id) {
		this.id = id;
	}

	public T getId() {
		return id;
	}
}
